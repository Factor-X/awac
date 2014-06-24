angular
.module('app.controllers')
.controller "MainCtrl", ($scope) ->
    $scope.isLoading = ->
        for k of $scope.initialLoad
            return true  unless $scope.initialLoad[k]
        false

    $scope.initialLoad =
        translations: false

    $scope.$on "LOAD_FINISHED", (event, args) ->
        if args.type is "TRANSLATIONS"
            $scope.initialLoad.translations = args.success
        return

    $scope.o =


        mainFuel:
            value: null
            reviewers:
                dataOwner: "XM"
                dataValidator: "JC"
                dataVerifier: "FC"
                dataLocker: "GP"




        myDouble:
            value: 17.97
            owner: "XM"
            validator: "JC"
            verifier: "FC"

        myText:
            value: 'Les poules discutent'
            reviewers:
                dataOwner: "XM"
                dataValidator: "JC"
                dataVerifier: "FC"
                dataLocker: "GP"

        myDoubleWithUnit:
            reviewers:
                dataOwner: "XM"
                dataValidator: "JC"
                dataVerifier: "FC"
                dataLocker: "GP"
            value: 17.97
            unit: 0
            units: [
                {
                    key: 12
                    value: "UNITS-KILOGRAM"
                }
                {
                    key: 14
                    value: "UNITS-GRAM"
                }
                {
                    key: 135
                    value: "UNITS-TON"
                }
            ]
