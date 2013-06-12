# Copyright (c) 2006-2011 by OpenLayers Contributors (see authors.txt for
# full list of contributors). Published under the Clear BSD license.
# See http://svn.openlayers.org/trunk/openlayers/license.txt for the
# full text of the license.

# Code transformed from JavaScript to CoffeeScript

# Modified onWheelEvent to allow wheel zoom even when no layer is loaded

OpenLayers.Handler.MouseWheel.onWheelEvent = (e) ->
  if (!@map || !@checkModifiers(e))
    return

  overScrollableDiv = false
  overLayerDiv = false
  overMapDiv = false
  elem = OpenLayers.Event.element(e)
  while((elem != null) && !overMapDiv && !overScrollableDiv)
    if (!overScrollableDiv)
      try
        if (elem.currentStyle)
          overflow = elem.currentStyle["overflow"]
        else
          style = document.defaultView.getComputedStyle(elem, null)
          overflow = style.getPropertyValue("overflow")
        overScrollableDiv = ( overflow && (overflow == "auto") || (overflow == "scroll") )
      catch err
        null

    if (!overLayerDiv)
      for layer in @map.layers
        if (elem == layer.div || elem == layer.pane)
          overLayerDiv = true;
          break;
    overMapDiv = (elem == @map.div)
    elem = elem.parentNode

  if (!overScrollableDiv && overMapDiv)
    delta = 0
    if (!e)
      e = window.event
    if (e.wheelDelta)
      delta = e.wheelDelta/120
      if (window.opera && window.opera.version() < 9.2)
        delta = -delta
    else if (e.detail)
        delta = -e.detail / 3
    @delta = this.delta + delta

    if(@interval)
      window.clearTimeout(this._timeoutId)
      @_timeoutId = window.setTimeout(OpenLayers.Function.bind((-> @wheelZoom(e)), @), @interval)
    else
      @wheelZoom(e)
    OpenLayers.Event.stop(e)
