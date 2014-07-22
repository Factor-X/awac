angular
.module('app.directives')
.directive "mmAwacModalLoading", (directiveService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-modal-loading.html"
    controller: ($scope, downloadService, translationService, $sce, $modal, $http) ->

        #change option of the modal
        $('#modalLoading').modal({
            backdrop: 'static'
        })
        $('#modalLoading').modal('hide')

        #initialize the modal when it's displayed
        $('#modalLoading').on 'shown.bs.modal', (e) ->
            #refresh angular
            $scope.$apply()
    link: (scope) ->

