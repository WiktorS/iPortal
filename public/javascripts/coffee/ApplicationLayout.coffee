PORTAL.setTopLevelLayout = ->
  lite =
    resizable: false
    slidable: false
    closable: false
    spacing_open: false
  layersSwitcherOptions =
    slidable: false
    resizerTip: "zmień rozmiar"
    togglerTip_open: "zamknij"
    togglerTip_closed: "otwórz"
    minSize: 250
    size: 450
    maxSize: 600
    spacing_open: 5
    spacing_closed: 5
    initClosed: $("#app_page .ui-layout-west").hasClass "initClosed"
  $("body").layout {defaults: lite}
  $("#app_page").layout
    west: layersSwitcherOptions
    center:
      onresize_end: -> PORTAL.map.updateSize()
  #Workaround for bootstrap & Opera 12 bug
  $('.modal').removeClass('fade') if (jQuery.browser.opera && parseInt(jQuery.browser.version) >= 12)

  logo = $("#app_header .logo").not(".background")
  logoBackground = $("#app_header .logo.background")
  logoBackground.width logo.width()
  logoBackground.height logo.height()

  arms = $("#app_header .arms").not(".background")
  armsBackground = $("#app_header .arms.background")
  armsBackground.width logo.width()
  armsBackground.height logo.height()
