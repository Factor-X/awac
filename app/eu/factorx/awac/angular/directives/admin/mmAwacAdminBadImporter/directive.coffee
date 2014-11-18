angular
.module('app.directives')
.directive "mmAwacAdminBadImporter", (directiveService, $timeout, ngTableParams, $http,$filter,downloadService,messageFlash,modalService) ->
	restrict: "E"
	scope: {}
	templateUrl: "$/angular/templates/mm-awac-admin-bad-importer.html"
	replace: true
	transclude: true
	link: (scope)->

		scope.total_bad= 0
		scope.total_info= 0
		scope.total_warning= 0
		scope.total_error= 0

		url = "awac/admin/badImporter/"+scope.$root.instanceName

		scope.import = ->

			scope.total_bad= 0
			scope.total_bad_info= 0
			scope.total_bad_warning= 0
			scope.total_bad_error= 0

			scope.total_question= 0
			scope.total_question_info= 0
			scope.total_question_warning= 0
			scope.total_question_error= 0

			modalService.show(modalService.LOADING)

			downloadService.getJson url , (result) ->
				if not result.success
					modalService.close(modalService.LOADING)
				else

					logBad = []
					logQuestion = []

					for logLine in result.data.logLines


						logLine.messagesWarningNb = 0
						logLine.messagesInfoNb = 0
						logLine.messagesErrorNb = 0


						if logLine.logCategory == 'BAD'
							scope.total_bad++
							logBad.push(logLine)

							if logLine.messages['WARNING']?
								logLine.messagesWarningNb = logLine.messages['WARNING'].length
								scope.total_bad_warning++

							if logLine.messages['INFO']?
								logLine.messagesInfoNb = logLine.messages['INFO'].length
								scope.total_bad_info++

							if logLine.messages['ERROR']?
								logLine.messagesErrorNb = logLine.messages['ERROR'].length
								scope.total_bad_error++


						if logLine.logCategory == 'QUESTION'
							scope.total_question++
							logQuestion.push(logLine)

							if logLine.messages['WARNING']?
								logLine.messagesWarningNb = logLine.messages['WARNING'].length
								scope.total_question_warning++

							if logLine.messages['INFO']?
								logLine.messagesInfoNb = logLine.messages['INFO'].length
								scope.total_question_info++

							if logLine.messages['ERROR']?
								logLine.messagesErrorNb = logLine.messages['ERROR'].length
								scope.total_question_error++


					scope.tableParams = new ngTableParams(
						page: 1 # show first page
						count: 100 # count per page
						sorting:
							code: "asc" # initial sorting
					,
						total: 0 # length of data
						getData: ($defer, params) ->
							orderedData = $filter("orderBy")(logBad, params.orderBy())
							$defer.resolve orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count())
							params.total logBad.length
					)

					scope.tableParams2 = new ngTableParams(
						page: 1 # show first page
						count: 100 # count per page
						sorting:
							code: "asc" # initial sorting
					,
						total: 0 # length of data
						getData: ($defer, params) ->
							orderedData = $filter("orderBy")(logQuestion, params.orderBy())
							$defer.resolve orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count())
							params.total logQuestion.length
					)

					modalService.close(modalService.LOADING)

		return