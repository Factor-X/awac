#
# Routes
#
initializeAdminRoutes = (defaultResolve) ->
    angular
    .module('app')
    .run ($rootScope, $location) ->
        $rootScope.onFormPath = (period, scope) ->
            $location.path($rootScope.getFormPath() + '/' + period + '/' + scope)
        $rootScope.getDefaultRoute = ()->
            return '/driver'

    angular
    .module('app')
    .config ($routeProvider) ->
        $routeProvider
        .when('/driver', {
                templateUrl: '$/angular/views/admin/driver.html'
                controller: 'AdminDriverManageCtrl'
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/factors', {
                templateUrl: '$/angular/views/admin/factors.html'
                controller: 'AdminFactorsManagerCtrl'
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/advice', {
                templateUrl: '$/angular/views/admin/manage_advices.html'
                controller: 'AdminAdvicesManagerCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/translation', { redirectTo: '/translation/interface' })
        .when('/translation/interface', {
                templateUrl: '$/angular/views/admin/translation_interface.html'
                controller: "AdminTranslationInterfaceCtrl"
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/translation/error-messages', {
                templateUrl: '$/angular/views/admin/translation_single_list.html'
                controller: "AdminTranslationSingleListCtrl"
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                    state: () ->
                        return 'error-message'
                }, defaultResolve)
            }
        )
        .when('/translation/emails', {
                templateUrl: '$/angular/views/admin/translation_single_list.html'
                controller: "AdminTranslationSingleListCtrl"
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                    state: () ->
                        return 'emails'
                }, defaultResolve)
            }
        )
        .when('/translation/forms', {
                templateUrl: '$/angular/views/admin/translation_surveys.html'
                controller: "AdminTranslationSurveysLabelsCtrl"
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/translation/base-lists', {
                templateUrl: '$/angular/views/admin/translation_base_lists.html'
                controller: "AdminTranslationBaseListCtrl"
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/translation/linked-lists', {
                templateUrl: '$/angular/views/admin/translation_linked_lists.html'
                controller: "AdminTranslationLinkedListCtrl"
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/translation/sub-lists', {
                templateUrl: '$/angular/views/admin/translation_sub_lists.html'
                controller: "AdminTranslationSubListCtrl"
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/translation/helps', {
                templateUrl: '$/angular/views/admin/translation_helps.html'
                controller: "AdminTranslationHelpsCtrl"
                resolve: angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/average', {
                templateUrl: '$/angular/views/admin/average.html'
                controller: 'AdminAverageCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )
        .when('/organization_data', {
                templateUrl: '$/angular/views/admin/organization_data.html'
                controller: 'AdminOrganizationDataCtrl'
                resolve:angular.extend({
                    displayLittleFormMenu: () ->
                        return true
                }, defaultResolve)
            }
        )

        .otherwise({ redirectTo: '/login' })

        return
