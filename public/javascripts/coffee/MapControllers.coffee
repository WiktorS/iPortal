window.createControllers = ->

  window.map.addControl new OpenLayers.Control.Scale "open_layers_status_scale", {
    div:
      OpenLayers.Util.getElement "open_layers_status_scale"
    updateScale:
      -> this.element.innerHTML = OpenLayers.i18n "Scale = 1 : ${scaleDenom}", {'scaleDenom': Math.round this.map.getScale()}
  }


  window.map.addControl new OpenLayers.Control.ScaleLine {
    div:
      OpenLayers.Util.getElement "open_layers_status_scaleline"
    bottomInUnits:
      ""
    bottomOutUnits:
      ""
  }


  window.map.addControl new OpenLayers.Control.MousePosition {
    div:
      OpenLayers.Util.getElement "open_layers_status_coords_map"
    emptyString:
      "współrzędne (szer./dł.)"
    formatOutput:
      (lonLat) -> OpenLayers.AppUtils.getFormattedLonLat(lonLat.lat, 'lat') + " " + OpenLayers.AppUtils.getFormattedLonLat(lonLat.lon, 'lon')
  }


  window.map.addControl new OpenLayers.Control.MousePosition {
    div:
      OpenLayers.Util.getElement "open_layers_status_coords_lonlat"
    emptyString:
      "współrzędne (1992)"
    prefix:
      "X: "
    separator:
      " Y: "
    displayProjection:
      window.map.projection
    numDigits:
      2
    formatOutput:
      (lonLat) -> this.prefix + lonLat.lat.toFixed(parseInt this.numDigits) + this.separator + lonLat.lon.toFixed(parseInt this.numDigits)
  }


  window.map.addControl new OpenLayers.Control.PanZoomBar


  window.map.addControl new OpenLayers.Control.Navigation {
    zoomBoxEnabled:
      true
    zoomWheelEnabled:
      true
  }


  window.map.addControls [window.zoomIn, window.zoomOut]

  $("#open_layers_button_extent").click ->
    window.map.zoomToMaxExtent()

  $("#open_layers_button_zoom_in").click ->
    $(this).toggleClass "active"
    if $(this).hasClass "active"
      $("#open_layers_button_zoom_out").removeClass "active"
      window.zoomOut.deactivate()
      window.zoomIn.activate()
    else
      window.zoomIn.deactivate()

  $("#open_layers_button_zoom_out").click ->
    $(this).toggleClass "active"
    if $(this).hasClass "active"
      $("#open_layers_button_zoom_in").removeClass "active"
      window.zoomIn.deactivate()
      window.zoomOut.activate()
    else
      window.zoomOut.deactivate()
