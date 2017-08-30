<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!--
  Copyright (c) 2011 Google Inc.
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy of
  the License at
  http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations under
  the License.
  To run this sample, set apiKey to your application's API key and clientId to
  your application's OAuth 2.0 client ID. They can be generated at:
    https://console.developers.google.com/apis/credentials?project=_
  Then, add a JavaScript origin to the client that corresponds to the domain
  where you will be running the script. Finally, activate the People API at:
    https://console.developers.google.com/apis/library?project=_
-->
<!DOCTYPE html>
<html>
  <head>
    <title>Say hello using the People API</title>
    <meta charset='utf-8' />
  </head>
  <body>
    <p>Say hello using the People API.</p>

    <!--Add buttons to initiate auth sequence and sign out-->
    <button id="authorize-button" style="display: none;">Authorize</button>
    <button id="signout-button" style="display: none;">Sign Out</button>

    <div id="content"></div>

    <script type="text/javascript">
      // Enter an API key from the Google API Console:
      //   https://console.developers.google.com/apis/credentials
      var apiKey = 'AIzaSyAuhkeREPdlVTQJdaHdto94wfqq-zzBjn0';
      // Enter the API Discovery Docs that describes the APIs you want to
      // access. In this example, we are accessing the People API, so we load
      // Discovery Doc found here: https://developers.google.com/people/api/rest/
      var discoveryDocs = ["https://people.googleapis.com/$discovery/rest?version=v1"];
      // Enter a client ID for a web application from the Google API Console:
      //   https://console.developers.google.com/apis/credentials?project=_
      // In your API Console project, add a JavaScript origin that corresponds
      //   to the domain where you will be running the script.
      var clientId = '750857276898-1nrl3bt409oohokl0dlsni89q5ruig12.apps.googleusercontent.com';
      // Enter one or more authorization scopes. Refer to the documentation for
      // the API or https://developers.google.com/people/v1/how-tos/authorizing
      // for details.
      var scopes = 'profile';
      var authorizeButton = document.getElementById('authorize-button');
      var signoutButton = document.getElementById('signout-button');
      function handleClientLoad() {
        // Load the API client and auth2 library
        gapi.load('client:auth2', initClient);
      }
      function initClient() {
        gapi.client.init({
            apiKey: apiKey,
            discoveryDocs: discoveryDocs,
            clientId: clientId,
            scope: scopes
        }).then(function () {
          // Listen for sign-in state changes.
          gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);
          // Handle the initial sign-in state.
          updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
          authorizeButton.onclick = handleAuthClick;
          signoutButton.onclick = handleSignoutClick;
        });
      }
      function updateSigninStatus(isSignedIn) {
        if (isSignedIn) {
          authorizeButton.style.display = 'none';
          signoutButton.style.display = 'block';
          makeApiCall();
        } else {
          authorizeButton.style.display = 'block';
          signoutButton.style.display = 'none';
        }
      }
      function handleAuthClick(event) {
        gapi.auth2.getAuthInstance().signIn();
      }
      function handleSignoutClick(event) {
        gapi.auth2.getAuthInstance().signOut();
      }
      // Load the API and make an API call.  Display the results on the screen.
      function makeApiCall() {
        gapi.client.people.people.get({
          'resourceName': 'people/me',
          'requestMask.includeField': 'person.names'
        }).then(function(resp) {
          var p = document.createElement('p');
          var name = resp.result.names[0].givenName;
          p.appendChild(document.createTextNode('Hello, '+name+'!'));
          document.getElementById('content').appendChild(p);
        });
      }
    </script>
    <script async defer src="https://apis.google.com/js/api.js" 
      onload="this.onload=function(){};handleClientLoad()" 
      onreadystatechange="if (this.readyState === 'complete') this.onload()">
    </script>
  </body>
</html>
