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
        $scope.statusOptions = $scope.getParams().statusOptions
        $scope.gwpUnits = $scope.getParams().gwpUnits

        $scope.action = $scope.getParams().action
        $scope.editMode = !!$scope.action
        if (!$scope.editMode)
            $scope.action = {}

        $scope.getDefaultDueDate = () ->
            defaultDueDate = new Date()
            defaultDueDate.setFullYear(defaultDueDate.getFullYear() + 1)
            return defaultDueDate

        $scope.title =
            inputName: 'title'
            field: $scope.action.title
            fieldTitle: "REDUCTION_ACTION_FORM_TITLE_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_TITLE_FIELD_PLACEHOLDER"
            validationRegex: "^.{1,255}$"
            validationMessage: "REDUCTION_ACTION_TITLE_WRONG_LENGTH"
            hideIsValidIcon: true
            focus: ->
                return true

        $scope.typeKey =
            inputName: 'typeKey'
            field: $scope.action.typeKey ? "1" # default type: REDUCING_GES
            fieldTitle: "REDUCTION_ACTION_FORM_TYPE_FIELD_TITLE"

        $scope.statusKey =
            inputName: 'statusKey'
            field: $scope.action.statusKey ? "1" # default status: RUNNING
            fieldTitle: "REDUCTION_ACTION_FORM_STATUS_FIELD_TITLE"

        $scope.scopeTypeKey =
            inputName: 'scopeTypeKey'
            field: $scope.action.scopeTypeKey ? "1" # default scope type: ORG
            fieldTitle: "REDUCTION_ACTION_FORM_SCOPE_TYPE_FIELD_TITLE"

        $scope.scopeId =
            inputName: 'scopeId'
            field: if (!!$scope.action.scopeId && ($scope.action.scopeTypeKey == "2")) then $scope.action.scopeId else $scope.$root.mySites[0].id # default site (first)

        $scope.physicalMeasure =
            inputName: 'physicalMeasure'
            field: $scope.action.physicalMeasure
            fieldTitle: "REDUCTION_ACTION_FORM_PHYSICAL_MEASURE_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_PHYSICAL_MEASURE_FIELD_PLACEHOLDER"
            validationRegex: "^.{0,255}$"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.ghgBenefit =
            inputName: 'ghgBenefit'
            field: $scope.action.ghgBenefit
            numbersOnly: 'double'
            fieldTitle: "REDUCTION_ACTION_FORM_GHG_BENEFIT_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_GHG_BENEFIT_FIELD_PLACEHOLDER"

        $scope.ghgBenefitUnitKey = $scope.action.ghgBenefitUnitKey ? "U5335" # default unit: kgCO2e


        $scope.financialBenefit =
            inputName: 'financialBenefit'
            field: $scope.action.financialBenefit
            numbersOnly: 'double'
            fieldTitle: "REDUCTION_ACTION_FORM_FINANCIAL_BENEFIT_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_FINANCIAL_BENEFIT_FIELD_PLACEHOLDER"

        $scope.investmentCost =
            inputName: 'investment'
            field: $scope.action.investmentCost
            numbersOnly: 'double'
            fieldTitle: "REDUCTION_ACTION_FORM_INVESTMENT_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_INVESTMENT_FIELD_PLACEHOLDER"

        $scope.expectedPaybackTime =
            inputName: 'expectedPaybackTime'
            field: $scope.action.expectedPaybackTime
            validationRegex: "^.{0,255}$"
            fieldTitle: "REDUCTION_ACTION_FORM_EXPECTED_PAYBACK_TIME_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_EXPECTED_PAYBACK_TIME_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.dueDate =
            inputName: 'dueDate'
            field: $scope.action.dueDate ? $scope.getDefaultDueDate()
            fieldTitle: "REDUCTION_ACTION_FORM_DUE_DATE_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_DUE_DATE_FIELD_PLACEHOLDER"
            minValue: new Date()
            validationMessage: "REDUCTION_ACTION_INVALID_DUE_DATE"
            hideIsValidIcon: true

        $scope.webSite =
            inputName: 'webSite'
            field: $scope.action.webSite
            validationRegex: "^.{0,255}$"
            fieldTitle: "REDUCTION_ACTION_FORM_WEBSITE_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_WEBSITE_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.responsiblePerson =
            inputName: 'responsiblePerson'
            field: $scope.action.responsiblePerson
            validationRegex: "^.{0,255}$"
            fieldTitle: "REDUCTION_ACTION_FORM_RESPONSIBLE_PERSON_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_RESPONSIBLE_PERSON_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.comment =
            inputName: 'comment'
            field: $scope.action.comment
            fieldType: 'textarea'
            validationRegex: "^.{0,1000}$"
            fieldTitle: "REDUCTION_ACTION_FORM_COMMENT_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FORM_COMMENT_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_1000_CHARACTERS"
            hideIsValidIcon: true

        $scope.allFieldValid = () ->
            return ($scope.title.isValid &&
                $scope.physicalMeasure.isValid &&
                $scope.expectedPaybackTime.isValid &&
                $scope.dueDate.isValid &&
                $scope.webSite.isValid &&
                $scope.responsiblePerson.isValid &&
                $scope.comment.isValid)

        #send the request to the server
        $scope.save = () ->
            $scope.isLoading = true

            data = {
                id: $scope.action.id
                title: $scope.title.field
                typeKey: $scope.typeKey.field
                statusKey: $scope.statusKey.field
                scopeTypeKey: $scope.scopeTypeKey.field
                scopeId: if ($scope.scopeTypeKey.field == "1") then null else $scope.scopeId.field
                physicalMeasure: $scope.physicalMeasure.field
                ghgBenefit: $scope.ghgBenefit.field
                ghgBenefitUnitKey: $scope.ghgBenefitUnitKey
                financialBenefit: $scope.financialBenefit.field
                investmentCost: $scope.investmentCost.field
                expectedPaybackTime: $scope.expectedPaybackTime.field
                dueDate: $scope.dueDate.field
                webSite: $scope.webSite.field
                responsiblePerson: $scope.responsiblePerson.field
                comment: $scope.comment.field
            }

            downloadService.postJson '/awac/actions/save', data, (result) ->
                if result.success
                    messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                    $scope.close()
                    $scope.getParams().cb()
                else
                    messageFlash.displayError result.data.message
                    $scope.isLoading = false

            return false

        $scope.toggleGhgBenefitField = (typeKey) ->
            if (typeKey == '2') # "better method"
                $scope.ghgBenefit.field = ""
                $scope.ghgBenefit.disabled = true
                $scope.ghgBenefitUnitKey.disabled = true
            else
                $scope.ghgBenefit.disabled = false
                $scope.ghgBenefitUnitKey.disabled = false

        # disabled GHG benefit field if action is not an effective reduction ("better method" type)
        $scope.$watch 'typeKey.field', (n, o) ->
            if (n != o)
                $scope.toggleGhgBenefitField(n)

        $scope.close = ->
            modalService.close modalService.CREATE_REDUCTION_ACTION

        $scope.toggleGhgBenefitField($scope.typeKey.field)

        console.log($scope)
        link: (scope) ->

