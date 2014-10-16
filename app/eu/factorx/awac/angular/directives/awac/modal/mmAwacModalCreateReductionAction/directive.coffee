angular
.module('app.directives')
.directive "mmAwacModalCreateReductionAction", (directiveService, downloadService, translationService, messageFlash, $filter) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-create-reduction-action.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.typeOptions = $scope.getParams().typeOptions
        $scope.gwpUnits = $scope.getParams().gwpUnits
        $scope.defaultGwpUnit = _.findWhere($scope.gwpUnits, {code: "U5335"}).code
        ###
              if editionMode
                    $scope.statusOptions = $scope.getParams().statusOptions
        ###

        $scope.title =
            inputName: 'title'
            field: ""
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_TITLE_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_TITLE_FIELD_PLACEHOLDER"
            validationRegex: "^.{1,255}$"
            validationMessage: "REDUCTION_ACTION_TITLE_WRONG_LENGTH"
            hideIsValidIcon: true
            focus: ->
                return true

        $scope.typeKey =
            inputName: 'typeKey'
            field: "1"
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_TYPE_FIELD_TITLE"

        $scope.$watch 'typeKey.field', (n, o) ->
            if (n != o)
               if (n == '2')
                    console.log("Disabling 'ghgBenefit' field ('Better method' selected)")
                    $scope.ghgBenefit.disabled = true
                    $scope.ghgBenefitUnitKey.disabled = true
                else
                    console.log("Enabling 'ghgBenefit' field")
                    $scope.ghgBenefit.disabled = false
                    $scope.ghgBenefitUnitKey.disabled = false

        $scope.scopeTypeKey =
            inputName: 'scopeTypeKey'
            field: "1"
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_SCOPE_TYPE_FIELD_TITLE"

        $scope.scopeId =
            inputName: 'scopeId'
            field: $scope.$root.mySites[0].id

        $scope.physicalMeasure =
            inputName: 'physicalMeasure'
            field: ""
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_PHYSICAL_MEASURE_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_PHYSICAL_MEASURE_FIELD_PLACEHOLDER"
            validationRegex: "^.{0,255}$"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.ghgBenefit =
            inputName: 'ghgBenefit'
            field: ""
            numbersOnly: 'double'
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_GHG_BENEFIT_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_GHG_BENEFIT_FIELD_PLACEHOLDER"

        $scope.ghgBenefitUnitKey = $scope.defaultGwpUnit

        $scope.financialBenefit =
            inputName: 'financialBenefit'
            field: ""
            numbersOnly: 'double'
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_FINANCIAL_BENEFIT_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_FINANCIAL_BENEFIT_FIELD_PLACEHOLDER"

        $scope.investment =
            inputName: 'investment'
            field: ""
            numbersOnly: 'double'
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_INVESTMENT_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_INVESTMENT_FIELD_PLACEHOLDER"

        $scope.expectedPaybackTime =
            inputName: 'expectedPaybackTime'
            field: ""
            validationRegex: "^.{0,255}$"
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_EXPECTED_PAYBACK_TIME_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_EXPECTED_PAYBACK_TIME_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        now = new Date()
        defaultYear = now.getFullYear() + 1
        defaultDueDate = now.setFullYear(defaultYear);

        $scope.dueDate =
            inputName: 'dueDate'
            field: defaultDueDate
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_DUE_DATE_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_DUE_DATE_FIELD_PLACEHOLDER"
            minValue: new Date()
            validationMessage: "REDUCTION_ACTION_INVALID_DUE_DATE"
            hideIsValidIcon: true

        $scope.webSite =
            inputName: 'webSite'
            field: ""
            validationRegex: "^.{0,255}$"
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_WEBSITE_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_WEBSITE_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.responsiblePerson =
            inputName: 'responsiblePerson'
            field: ""
            validationRegex: "^.{0,255}$"
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_RESPONSIBLE_PERSON_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_RESPONSIBLE_PERSON_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.comment =
            inputName: 'comment'
            field: ""
            fieldType: 'textarea'
            validationRegex: "^.{0,1000}$"
            fieldTitle: "CREATE_REDUCTION_ACTION_FORM_COMMENT_FIELD_TITLE"
            placeholder: "CREATE_REDUCTION_ACTION_FORM_COMMENT_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_1000_CHARACTERS"
            hideIsValidIcon: true

        $scope.allFieldValid = () ->
            if ($scope.title.isValid && $scope.physicalMeasure.isValid && $scope.expectedPaybackTime.isValid && $scope.dueDate.isValid && $scope.webSite.isValid && $scope.responsiblePerson.isValid && $scope.comment.isValid)
                return true
            return false

        #send the request to the server
        $scope.save = () ->
            $scope.isLoading = true

            data =
                title: $scope.title.field
                scopeTypeKey: $scope.scopeTypeKey.field
                scopeId: $scope.scopeId.field
                typeKey: $scope.typeKey.field
                physicalMeasure: $scope.physicalMeasure.field
                ghgBenefit: $scope.ghgBenefit.field
                ghgBenefitUnitKey: $scope.ghgBenefitUnitKey
                financialBenefit: $scope.financialBenefit.field
                investment: $scope.investment.field
                expectedPaybackTime: $scope.expectedPaybackTime.field
                dueDate: $scope.dueDate.field
                webSite: $scope.webSite.field
                responsiblePerson: $scope.responsiblePerson.field
                comment: $scope.comment.field

            downloadService.postJson '/awac/actions/save', data, (result) ->
                if result.success
                    messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                    $scope.close()
                    $scope.getParams().cb()
                else
                    messageFlash.displayError result.data.message
                    $scope.isLoading = false

            return false

        $scope.close = ->
            modalService.close modalService.CREATE_REDUCTION_ACTION

        link: (scope) ->

