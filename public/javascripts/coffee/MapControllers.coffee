PORTAL.createControllers = ->

  PORTAL.map.addControl new OpenLayers.Control.Scale "open_layers_status_scale", {
    div:
      OpenLayers.Util.getElement "open_layers_status_scale"
    updateScale:
      -> this.element.innerHTML = OpenLayers.i18n "Scale = 1 : ${scaleDenom}", {'scaleDenom': Math.round this.map.getScale()}
  }


  PORTAL.map.addControl new OpenLayers.Control.ScaleBar {
    div: OpenLayers.Util.getElement("open_layers_status_scalebar")
    divisions: 4
    subdivisions: 1
    abbreviateLabel: true
    singleLine: true
  }


  PORTAL.map.addControl new OpenLayers.Control.MousePosition {
    div:
      OpenLayers.Util.getElement "open_layers_status_coords_map"
    emptyString:
      "współrzędne (szer./dł.)"
    formatOutput:
      (lonLat) -> OpenLayers.AppUtils.getFormattedLonLat(lonLat.lat, 'lat') + " " + OpenLayers.AppUtils.getFormattedLonLat(lonLat.lon, 'lon')
  }


  PORTAL.map.addControl new OpenLayers.Control.MousePosition {
    div:
      OpenLayers.Util.getElement "open_layers_status_coords_lonlat"
    emptyString:
      "współrzędne (1992)"
    prefix:
      "X: "
    separator:
      " Y: "
    displayProjection:
      PORTAL.map.projection
    numDigits:
      0
    formatOutput:
      (lonLat) -> this.prefix + lonLat.lat.toFixed(parseInt this.numDigits) + this.separator + lonLat.lon.toFixed(parseInt this.numDigits)
  }


  PORTAL.map.addControl new OpenLayers.Control.PanZoomBar {
    position: new OpenLayers.Pixel(4, 34)
  }


  PORTAL.map.addControl new OpenLayers.Control.Navigation {
    zoomBoxEnabled:
      true
    zoomWheelEnabled:
      true
  }

  PORTAL.map.addControls [PORTAL.zoomIn, PORTAL.navHistory]

  infoControl = new OpenLayers.Control.WMSGetFeatureInfo
    title: 'Wybierz obiekt',
    layers: [],
    url: null,
    queryVisible: false
  PORTAL.map.addControl infoControl
  infoControl.events.register "getfeatureinfo", this, (evt) ->
    $("#wmsQueryResult").html evt.text
    $("#wmsQueryResult").find("style").remove()
    $("#wmsQueryResult").find("table")
      .addClass("table")
      .addClass("table-bordered")
      .addClass("table-hover")
      .addClass("table-striped")
    $("#wmsQueryResult a[href^='http://']").attr("target","_blank")
    if evt.text.indexOf("ServiceException") == -1 && !$("#wmsQueryResult").find("table").length
      $("#wmsQueryResult").html "Brak danych"
    $("#wmsInformation").modal()
  infoControl.events.register "beforegetfeatureinfo", this, (evt) ->
    activeLayers = []
    $("#app_layers .tier3 input").each (i, e) ->
      mapLayer = PORTAL.Utils.findLayer PORTAL.Utils.buildIdWithPrefix($(this).attr("id"), "layer")
      if mapLayer.getVisibility() && $(this).data("queryable")
        activeLayers.push mapLayer
    infoControl.layers = activeLayers
    infoControl.url = null
    if (!activeLayers.length)
      $("#wmsQueryResult").html "Nie wybrano warstw z których można pobrać informacje o obiektach"
      $("#wmsInformation").modal()

  $("#open_layers_button_info").click ->
    if !$(this).hasClass("active")
      infoControl.activate()
    else
      infoControl.deactivate()

  $("#open_layers_button_history_prev").click ->
    PORTAL.navHistory.previous.trigger()

  $("#open_layers_button_history_next").click ->
    PORTAL.navHistory.next.trigger()

  $("#open_layers_button_extent").click ->
    sourceId = PORTAL.configurationSettings.systhermSourceId
    serviceId = PORTAL.configurationSettings.systhermServiceId
    if (sourceId && serviceId)
      setMapOnServiceLocation = (element) ->
        coordinates = element.data("location").split "|"
        PORTAL.map.setCenter new OpenLayers.LonLat(coordinates[1], coordinates[0]), coordinates[2]
      setMapOnServiceLocation $("#toggler-"+sourceId+"-"+serviceId).siblings(".service-showlocation")
    else
      PORTAL.map.zoomToMaxExtent()

  $("#open_layers_button_zoom_in").click ->
    $(this).toggleClass "active"
    if $(this).hasClass "active"
      PORTAL.zoomIn.activate()
    else
      PORTAL.zoomIn.deactivate()


  $("#open_layers_button_save_url").click ->
    $("#getUrlModal input").val(
      "http://" + location.host +
      "/x/" + PORTAL.map.getCenter().lat.toFixed(0) +
      "/y/" + PORTAL.map.getCenter().lon.toFixed(0) +
      "/z/" + PORTAL.map.getZoom()
    ).select()

  $("#getUrlModal a").click -> $("#getUrlModal").modal "hide"


  $("#open_layers_button_history_prev").tooltip {
    title: PORTAL.messages.historyPrev,
    placement: "bottom"
  }
  $("#open_layers_button_history_next").tooltip {
    title: PORTAL.messages.historyNext,
    placement: "bottom"
  }
  $("#open_layers_button_extent").tooltip {
    title: PORTAL.messages.zoomToExtent,
    placement: "bottom"
  }
  $("#open_layers_button_zoom_in").tooltip {
    title: PORTAL.messages.zoomIn,
    placement: "bottom"
  }
  $("#open_layers_button_save_url").tooltip {
    title: PORTAL.messages.saveUrl,
    placement: "bottom"
  }
  $("#open_layers_button_go_to_location").tooltip {
    title: PORTAL.messages.goToLocation,
    placement: "bottom"
  }

  $("#open_layers_button_help").tooltip {
  title: PORTAL.messages.help,
  placement: "bottom"
  }
