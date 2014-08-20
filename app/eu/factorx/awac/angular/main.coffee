#
# Modules
#

angular.module 'app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']

angular.module 'app.filters', []

angular.module 'app.services', []

angular.module 'app.controllers', ['app.services', 'ngRoute', 'angular-flash.service',
                                   'angular-flash.flash-alert-directive',
                                   'angularFileUpload','tmh.dynamicLocale']

angular.module 'app', [
    'app.directives',
    'app.filters',
    'app.services',
    'app.controllers'
]


angular.module("tmh.dynamicLocale").config (tmhDynamicLocaleProvider)->
    tmhDynamicLocaleProvider.localeLocationPattern('assets/components/angular-i18n/angular-locale_{{locale}}.js')


#
# Routes
#
initializeCommonRoutes()
iName = document.querySelector("meta[name=app]").getAttribute("content")
if iName == "municipality"
    initializeMunicipalityRoutes()
if iName == "enterprise"
    initializeEnterpriseRoutes()
