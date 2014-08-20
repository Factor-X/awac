angular
.module('app.directives')
.directive "mmAwacGraphDonut", ($sce, $filter) ->
    restrict: "E"
    scope:
        ngItems: '='
    templateUrl: "$/angular/templates/mm-awac-graph-donut.html"
    replace: true
    link: (scope, element) ->
        scope.legend = ''

        scope.$watch 'ngItems', () ->
            if scope.ngItems?
                ctx = $('.holder', element).get(0).getContext('2d')

                data = angular.copy(scope.ngItems)

                f = $filter('numberToI18N')
                total = 0
                for d, i in data
                    total += d.value

                for d, i in data
                    color = tinycolor({h: 360.0 * i / (data.length), s: 0.5, l: 0.5})
                    colorh = tinycolor({h: 360.0 * i / (data.length), s: 0.75, l: 0.66})

                    d.color = color
                    d.highlight = colorh
                    d.label += ' (<b>' + f(100.0 * d.value / total) + '%</b>)'

                myDoughnutChart = new Chart(ctx).Doughnut(data, {
                    tooltipTemplate: (o) ->
                        f(100.0 * (o.endAngle - o.startAngle) / (Math.PI * 2)) + "% (" + f(o.value) + " tCO2)"
                    animation: false
                    legendTemplate: "" +
                        "<% for (var i=0; i<segments.length; i++){%>" +
                        "<div><span class=\"chart-legend-bullet-color\" style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></div>" +
                        "<%}%>"

                })

                scope.legend = $sce.trustAsHtml(myDoughnutChart.generateLegend())

