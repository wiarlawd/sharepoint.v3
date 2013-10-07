// Copyright 2013 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.enterprise.connector.sharepoint.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.rpc.ServiceException;

import com.google.common.base.Strings;
import com.google.enterprise.connector.sharepoint.client.SPConstants;
import com.google.enterprise.connector.sharepoint.client.SharepointClientContext;
import com.google.enterprise.connector.sharepoint.client.Util;
import com.google.enterprise.connector.sharepoint.generated.userprofilechangeservice.UserProfileChangeData;
import com.google.enterprise.connector.sharepoint.generated.userprofilechangeservice.UserProfileChangeDataContainer;
import com.google.enterprise.connector.sharepoint.generated.userprofilechangeservice.UserProfileChangeQuery;
import com.google.enterprise.connector.sharepoint.generated.userprofilechangeservice.UserProfileChangeService;
import com.google.enterprise.connector.sharepoint.generated.userprofilechangeservice.UserProfileChangeServiceLocator;
import com.google.enterprise.connector.sharepoint.generated.userprofilechangeservice.UserProfileChangeServiceSoap_BindingStub;
import com.google.enterprise.connector.sharepoint.generated.userprofileservice.UserProfileService;
import com.google.enterprise.connector.sharepoint.generated.userprofileservice.UserProfileServiceLocator;
import com.google.enterprise.connector.sharepoint.generated.userprofileservice.UserProfileServiceSoap_BindingStub;
import com.google.enterprise.connector.sharepoint.social.SharePointSocialCheckpoint;
import com.google.enterprise.connector.sharepoint.spiimpl.SharepointException;
import com.google.enterprise.connector.sharepoint.wsclient.client.UserProfileChangeWS;
import com.google.enterprise.connector.spi.SpiConstants.ActionType;

public class UserProfileChangeHelper {
  private final Logger LOGGER = Logger.getLogger(
      UserProfileChangeHelper.class.getName());
  private SharepointClientContext sharepointClientContext;
  private UserProfileChangeServiceSoap_BindingStub stub;
  private String endpoint;

  public Map<String, ActionType> getChangedUserProfiles(
      SharePointSocialCheckpoint checkpoint) {
    try {
      if (stub == null) {
        LOGGER.warning(
            "Unable to get the list collection because stub is null");
        return null;
      }
      Map<String, ActionType> updatedProfiles = new HashMap<String, ActionType>();
      UserProfileChangeQuery changeQuery = new UserProfileChangeQuery();
      changeQuery.setDelete(true);
      changeQuery.setUserProfile(true);
      changeQuery.setUpdate(true);
      changeQuery.setUpdateMetadata(true);
      changeQuery.setSingleValueProperty(true);
      changeQuery.setMultiValueProperty(true);
      changeQuery.setColleague(true);
      UserProfileChangeDataContainer changeContainer= stub.getChanges(
          checkpoint.getUserProfileChangeToken(), changeQuery);
      if (changeContainer != null) {
        UserProfileChangeData[] changes = changeContainer.getChanges();
        if (changes != null && changes.length > 0) {
          for(UserProfileChangeData change : changes) {
            String userAccountName = change.getUserAccountName();
            if (Strings.isNullOrEmpty(userAccountName)) {
              continue;
            }
            for(String changeType : change.getChangeType()) {
              LOGGER.log(Level.INFO,
                  "User Profile Change Type Recevied = "+ changeType);
              if (SPConstants.DELETE.equalsIgnoreCase(changeType)) {
                LOGGER.log(Level.INFO,"User Profile Deleted for user = "
                    + change.getUserAccountName());
                updatedProfiles.put(userAccountName, ActionType.DELETE);
              } else {
                updatedProfiles.put(userAccountName, ActionType.ADD);
              }
            }
          }
        }
        LOGGER.log(Level.INFO, "User Profile Change Token Recevied = "
            + changeContainer.getChangeToken());
        if (changeContainer.isHasExceededCountLimit()) {
          checkpoint.setUserProfileChangeToken(
              changeContainer.getChangeToken());
        } else {
          checkpoint.setUserProfileChangeToken(getCurrentChangeToken());
        }
      }
      return updatedProfiles;
    } catch (final Exception e) {
      LOGGER.log(Level.WARNING,
          "Unable to get Updates for SharePoint user Profiles", e);
      return null;
    }
  }

  public UserProfileChangeHelper(
      SharepointClientContext inSharepointClientContext) {
    if (inSharepointClientContext != null) {
      sharepointClientContext = inSharepointClientContext;
      endpoint = Util.encodeURL(sharepointClientContext.getSiteURL())
          + SPConstants.USERPROFILECHANGEENDPOINT;
      LOGGER.log(Level.CONFIG,
          "UserProfileChangeWS Endpoint set to: " + endpoint);
      try {
        final UserProfileChangeServiceLocator loc =
            new UserProfileChangeServiceLocator();
        loc.setUserProfileChangeServiceSoapEndpointAddress(endpoint);

        final UserProfileChangeService service = loc;
        try {
          stub = (UserProfileChangeServiceSoap_BindingStub)
              service.getUserProfileChangeServiceSoap();
        } catch (final ServiceException e) {
          LOGGER.log(Level.WARNING, e.getMessage(), e);
          throw new SharepointException(
              "Unable to create the userprofile stub");
        }

        final String strDomain = inSharepointClientContext.getDomain();
        String strUserName = inSharepointClientContext.getUsername();
        final String strPassword = inSharepointClientContext.getPassword();

        strUserName = Util.getUserNameWithDomain(strUserName, strDomain);
        stub.setUsername(strUserName);
        stub.setPassword(strPassword);
        // The web service time-out value
        stub.setTimeout(sharepointClientContext.getWebServiceTimeOut());
        LOGGER.fine("Set time-out of : "
            + sharepointClientContext.getWebServiceTimeOut()
            + " milliseconds");
      } catch (final Exception e) {
        LOGGER.log(Level.WARNING,
            "Problem while creating the stub for UserProfile WS", e);
      }
    }
  }

  public String getCurrentChangeToken() throws Exception{
    try {
      return stub.getCurrentChangeToken();
    } catch (final Exception e) {
      LOGGER.log(Level.WARNING, e.getMessage(), e);
      throw new SharepointException("Unable to get Current Change Token");
    }
  }

}