/*
Copyright 2011 NativeDriver committers
Copyright 2011 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.google.android.testing.nativedriver.server.handler;

import com.google.android.testing.nativedriver.common.HasTouchScreen;
import com.google.android.testing.nativedriver.common.Touch;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.server.DriverSessions;
import org.openqa.selenium.remote.server.JsonParametersAware;
import org.openqa.selenium.remote.server.handler.WebDriverHandler;
import org.openqa.selenium.remote.server.rest.ResultType;

import java.util.Map;

/**
 * Handler of {@code /session/:sessionId/moveto} for interaction of
 * touch screen
 *
 * @author Dezheng Xu
 */
public class TouchMove extends WebDriverHandler implements JsonParametersAware {
  private static final String ELEMENT = "element";
  private String elementId;
  private boolean elementProvided = false;

  public TouchMove(DriverSessions sessions) {
    super(sessions);
  }

  @Override
  public ResultType call() throws Exception {
    Coordinates elementLocation = null;
    if (elementProvided) {
      WebElement element = getKnownElements().get(elementId);
      elementLocation = ((Locatable) element).getCoordinates();
    }
    Touch touch = ((HasTouchScreen) getDriver()).getTouch();
    touch.touchMove(elementLocation);
    return ResultType.SUCCESS;
  }

  @Override
  public void setJsonParameters(Map<String, Object> allParameters)
      throws Exception {
    if (allParameters.containsKey(ELEMENT)) {
      elementId = (String) allParameters.get(ELEMENT);
      elementProvided = true;
    } else {
      elementProvided = false;
    }
  }
}
