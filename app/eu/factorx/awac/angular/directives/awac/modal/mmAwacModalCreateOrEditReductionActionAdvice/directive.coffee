angular
.module('app.directives')
.directive "mmAwacModalCreateOrEditReductionActionAdvice", (directiveService, downloadService, translationService, messageFlash, $upload, $window, codeLabelHelper) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-create-or-edit-reduction-action-advice.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        interfaceTypeCodeLabels = $scope.getParams().interfaceTypeCodeLabels
        baseIndicatorCodeLabels = $scope.getParams().baseIndicatorCodeLabels
        actionAdvice = $scope.getParams().actionAdvice

        interfaceTypeKeys = ['enterprise', 'municipality', 'household', 'event', 'littleEmitter']
        $scope.interfaceTypeOptions = _.filter(interfaceTypeCodeLabels, (codeLabel) ->
            return _.contains(interfaceTypeKeys, codeLabel.key)
        )

        baseIndicatorLabelsByInterfaceType =
            enterprise: _.filter(baseIndicatorCodeLabels, (codeLabel) ->
                return codeLabel.key.startsWith("BI_")
            )
            municipality: _.filter(baseIndicatorCodeLabels, (codeLabel) ->
                return codeLabel.key.startsWith("BICo_")
            )
            household: []       # No BaseIndicators! TODO Fix this
            event: []           # No BaseIndicators! TODO Fix this
            littleEmitter: []   # No BaseIndicators! TODO Fix this

        $scope.editMode = !!actionAdvice
        if $scope.editMode
            $scope.actionAdvice = angular.copy(actionAdvice)
        else
            $scope.actionAdvice =
                interfaceTypeKey: interfaceTypeKeys[0] # default calculator
                typeKey: '1' # default type = 'Reducing measure'

        $scope.interfaceTypeKey =
            inputName: 'interfaceTypeKey'
            field: $scope.actionAdvice.interfaceTypeKey
            fieldTitle: "REDUCTION_ACTION_ADVICE_CALCULATOR_FIELD_TITLE"

        $scope.title =
            inputName: 'title'
            field: $scope.actionAdvice.title
            fieldTitle: "REDUCTION_ACTION_TITLE_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_TITLE_FIELD_PLACEHOLDER"
            validationRegex: "^.{1,255}$"
            validationMessage: "REDUCTION_ACTION_TITLE_WRONG_LENGTH"
            hideIsValidIcon: true
            focus: ->
                return true

        $scope.typeKey =
            inputName: 'typeKey'
            field: $scope.actionAdvice.typeKey
            fieldTitle: "REDUCTION_ACTION_TYPE_FIELD_TITLE"

        $scope.physicalMeasure =
            inputName: 'physicalMeasure'
            field: $scope.actionAdvice.physicalMeasure
            fieldTitle: "REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_PLACEHOLDER"
            fieldType: 'textarea'

        $scope.financialBenefit =
            inputName: 'financialBenefit'
            field: $scope.actionAdvice.financialBenefit
            numbersOnly: 'double'
            fieldTitle: "REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_PLACEHOLDER"

        $scope.investmentCost =
            inputName: 'investment'
            field: $scope.actionAdvice.investmentCost
            numbersOnly: 'double'
            fieldTitle: "REDUCTION_ACTION_INVESTMENT_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_INVESTMENT_FIELD_PLACEHOLDER"

        $scope.expectedPaybackTime =
            inputName: 'expectedPaybackTime'
            field: $scope.actionAdvice.expectedPaybackTime
            validationRegex: "^.{0,255}$"
            fieldTitle: "REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.webSite =
            inputName: 'webSite'
            field: $scope.actionAdvice.webSite
            validationRegex: "^.{0,255}$"
            fieldTitle: "REDUCTION_ACTION_WEBSITE_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_WEBSITE_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.responsiblePerson =
            inputName: 'responsiblePerson'
            field: $scope.actionAdvice.responsiblePerson
            validationRegex: "^.{0,255}$"
            fieldTitle: "REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS"
            hideIsValidIcon: true

        $scope.comment =
            inputName: 'comment'
            field: $scope.actionAdvice.comment
            fieldType: 'textarea'
            validationRegex: ".{0,1000}"
            fieldTitle: "REDUCTION_ACTION_COMMENT_FIELD_TITLE"
            placeholder: "REDUCTION_ACTION_COMMENT_FIELD_PLACEHOLDER"
            validationMessage: "TEXT_FIELD_MAX_1000_CHARACTERS"
            hideIsValidIcon: true

        $scope.files = $scope.actionAdvice.files ? []

        $scope.baseIndicatorAssociations = $scope.actionAdvice.baseIndicatorAssociations ? []

        $scope.baseIndicatorAssociationToAdd =
            baseIndicatorKey: ""
            percent: 0
            isValid: () ->
                if !!this.baseIndicatorKey && !!this.percent
                    percent = +this.percent.replace(',', '.')
                    return (percent > 0) && (percent < 100)
                return false
            clear: () ->
                this.baseIndicatorKey = ""
                this.percent = 0

        $scope.baseIndicatorOptions = []

        $scope.$watch 'interfaceTypeKey.field', (n, o) ->
            if (n != o)
                $scope.baseIndicatorAssociations = []
                $scope.baseIndicatorAssociationToAdd.clear()
                $scope.refreshBaseIndicatorOptions()
            return

        $scope.$watch 'typeKey.field', (n, o) ->
            if (n != o)
                $scope.baseIndicatorAssociations = []
                $scope.baseIndicatorAssociationToAdd.clear()
            return

        $scope.allFieldValid = () ->
            return ($scope.title.isValid &&
                $scope.webSite.isValid &&
                $scope.responsiblePerson.isValid &&
                (($scope.typeKey.field == "2") || ($scope.baseIndicatorAssociations.length > 0)))

        $scope.editorOptions = {
            language: 'fr'
            skin: 'moono'
            uiColor: '#CFCDC0'
            toolbar: 'Basic'
            height: '95px'
        }
        #send the request to the server
        $scope.save = () ->
            $scope.isLoading = true

            data = {
                id: $scope.actionAdvice.id
                interfaceTypeKey: $scope.interfaceTypeKey.field
                title: $scope.title.field
                typeKey: $scope.typeKey.field
                physicalMeasure: $scope.physicalMeasure.field
                financialBenefit: $scope.financialBenefit.field
                investmentCost: $scope.investmentCost.field
                expectedPaybackTime: $scope.expectedPaybackTime.field
                webSite: $scope.webSite.field
                responsiblePerson: $scope.responsiblePerson.field
                comment: $scope.comment.field
                files: $scope.files
                baseIndicatorAssociations: $scope.baseIndicatorAssociations
            }

            downloadService.postJson '/awac/admin/advices/save', data, (result) ->
                if result.success
                    messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                    if $scope.editMode
                        angular.extend($scope.getParams().actionAdvice, result.data)
                    $scope.close()
                    if !!$scope.getParams().cb
                        $scope.getParams().cb()
                else
                    $scope.isLoading = false

            return false

        $scope.close = ->
            modalService.close modalService.CREATE_OR_EDIT_REDUCTION_ACTION_ADVICE

        $scope.onFileSelect = ($files) ->
            $scope.inDownload = true

            #$files: an array of files selected, each file has name, size, and type.
            i = 0

            while i < $files.length
                file = $files[i]

                $scope.upload = $upload.upload(url: "/awac/file/upload/", file: file)
                .progress((evt) ->
                    $scope.percent = parseInt(100.0 * evt.loaded / evt.total)
                    return
                )
                .success((data) ->
                    # file is uploaded successfully
                    $scope.percent = 0
                    $scope.inDownload = false
                    fileName = data.name
                    messageFlash.displaySuccess("The file " + fileName + " was successfully uploaded ")

                    $scope.files.push(data)
                    console.log($scope.files)
                    return
                )
                .error((data, status, headers, config) ->
                    $scope.percent = 0
                    $scope.inDownload = false
                    messageFlash.displayError("The upload of the file has failed")
                    return
                )
                i++
            return

        $scope.download = (storedFileId) ->
            url = '/awac/file/download/' + storedFileId
            $window.open(url);

        $scope.removeFile = (storedFileId) ->
            for index, file of $scope.files
                if (file.id == storedFileId)
                    $scope.files.splice(index, 1);
                    break
            return

        $scope.refreshBaseIndicatorOptions = () ->
            baseIndicatorOptions = baseIndicatorLabelsByInterfaceType[$scope.interfaceTypeKey.field]
            baseIndicatorAssociations = $scope.baseIndicatorAssociations
            if (baseIndicatorAssociations.length > 0)
                # remove baseIndicators already associated
                selectedIndicatorsKeys = _.pluck(baseIndicatorAssociations, "baseIndicatorKey")
                baseIndicatorOptions = _.reject(baseIndicatorOptions, (codeLabel) ->
                    return _.contains(selectedIndicatorsKeys, codeLabel.key)
                )
            # sort by key
            baseIndicatorOptions = _.sortBy(baseIndicatorOptions, (codeLabel) ->
                return parseInt(codeLabel.key.match(/\d+/), 10)
            )
            $scope.baseIndicatorOptions = baseIndicatorOptions
            return

        $scope.sortBaseIndicatorAssociations = () ->
            $scope.baseIndicatorAssociations = _.sortBy($scope.baseIndicatorAssociations, (baseIndicatorAssociation) ->
                return parseInt(baseIndicatorAssociation.baseIndicatorKey.match(/\d+/), 10)
            )
            return

        $scope.addBaseIndicatorAssociation = () ->
            if ($scope.baseIndicatorAssociationToAdd.isValid())
                $scope.baseIndicatorAssociationToAdd.percent = +$scope.baseIndicatorAssociationToAdd.percent.replace(',', '.')
                $scope.baseIndicatorAssociations.push(angular.copy($scope.baseIndicatorAssociationToAdd))
                $scope.baseIndicatorAssociationToAdd.clear();

                $scope.sortBaseIndicatorAssociations();
                $scope.refreshBaseIndicatorOptions()
            return

        $scope.removeBaseIndicatorAssociation = (baseIndicatorKey) ->
            for index, baseIndicatorAssociation of $scope.baseIndicatorAssociations
                if (baseIndicatorAssociation.baseIndicatorKey == baseIndicatorKey)
                    $scope.baseIndicatorAssociations.splice(index, 1);
                    break
            $scope.refreshBaseIndicatorOptions()
            return

        $scope.getBaseIndicatorLabel = (baseIndicatorKey) ->
            return baseIndicatorKey + " - " + codeLabelHelper.getLabelByKey(baseIndicatorCodeLabels, baseIndicatorKey)

        # Get indicators options related to selected calculator
        $scope.refreshBaseIndicatorOptions()
        $scope.sortBaseIndicatorAssociations()

        link: (scope) ->

