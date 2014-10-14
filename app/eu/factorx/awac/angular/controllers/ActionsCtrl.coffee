angular
.module('app.controllers')
.controller "ActionsCtrl", ($scope, displayFormMenu, modalService) ->
    $scope.displayFormMenu = displayFormMenu

    $scope.pouet = true

    $scope.actions = [

        {
            opened: false
            title: 'Utiliser des mesures plus précises que "Usages"'
            type:
                icon: 'flag'
                text: 'Meilleure mesure'
            scope:
                icon: 'flag'
                text: 'Namur'
            realization:
                period: '2012'
        }
        ,
        {
            opened: false
            title: 'Utiliser des mesures plus précises que "Usages"'
            type:
                icon: 'flag'
                text: 'Meilleure mesure'
            scope:
                icon: 'flag'
                text: 'Namur'
            realization:
                period: '2012'
        }

    ]

    $scope.create = () ->
        modalService.show modalService.CREATE_REDUCTION_ACTION