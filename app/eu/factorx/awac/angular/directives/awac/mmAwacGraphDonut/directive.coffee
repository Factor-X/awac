angular
.module('app.directives')
.directive "mmAwacGraphDonut", () ->
    restrict: "E"
    scope:
        ngItems: '='
    templateUrl: "$/angular/templates/mm-awac-graph-donut.html"
    replace: true
    link: (scope, element) ->
        scope.$watch 'ngItems', () ->
            if scope.ngItems?
                ctx = $('.holder', element).get(0).getContext('2d')

                data = angular.copy(scope.ngItems)

                for d in data
                    d.color = '#aa6666'
                    d.highlight = '#ff9999'


                myDoughnutChart = new Chart(ctx).Doughnut(data, {
                    tooltipTemplate: "<%if (label){%><%=label%>: <%}%><%= value %>",
                    animation: false
                })
