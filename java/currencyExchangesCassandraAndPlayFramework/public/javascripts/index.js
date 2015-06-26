        var tip;
        function loadsymbols(){
            var text="";
            $.get( "/service/symbols", function( data ) {
                var symbols=data.split("\n");
                if(symbols.length<2){
                    text+="<div class='loadsymbols button2'>load more symbols</div><br />";
                    $("#othercurrenciesleft").html(text);
                    $("#othercurrenciesright").html(text);
                    $(".loadsymbols").click(loadsymbols);
                    return;
                }
                var n=0;
                for(;n<Math.round((symbols.length-1)/2);n++){
                  if(symbols[n].trim())
                    text+="<div id='see_"+symbols[n]+"' class='button0'>EUR vs "+symbols[n]+"</div><br />";
                }
                $("#othercurrenciesleft").html(text);
                text="";
                for(;n<symbols.length;n++){
                  if(symbols[n].trim())
                    text+="<div id='see_"+symbols[n]+"' class='button0'>EUR vs "+symbols[n]+"</div><br />";
                }
                $("#othercurrenciesright").html(text);
                $(".button0").click(function(){
                    loadChart(this.id.split("_")[1]);
                });
            });
        }
        function loadChart(currency){
            var width = 960, height = 500;
            var y = d3.scale.linear().range([height, 0]);
            var chart = d3.select(".chart").attr("width", width).attr("height", height);
            tip = d3.tip()
                    .attr('class', 'd3-tip')
                    .html(function(d) {
                        return "<strong>"+d.name+":</strong> <span style='color:red'>"+d.value+" </span>";
                    });
                    
            chart.call(tip);

            d3.tsv("/service/exchange/"+currency, type, function(error, data) {
                d3.select(".chart").selectAll("*").remove();;
                $("#vs").html(currency);
                y.domain([0, d3.max(data, function(d) { return d.value; })]);
                var barWidth = width / data.length;
                var bar = chart.selectAll("g")
                   .data(data)
                   .enter().append("g")
                   .attr("transform", function(d, i) { return "translate(" + i * barWidth + ",0)"; });

                bar.append("rect")
                    .attr("y", function(d) { return y(d.value); })
                    .attr("height", function(d) { return height - y(d.value); })
                    .on('mouseover', tip.show)
                    .on('mouseout', tip.hide)
                    .attr("width", barWidth - 1);

                bar.append("text")
                    .attr("x", barWidth / 2)
                    .attr("y", function(d) { return y(d.value) + 3; })
                    .attr("dy", ".75em")
                    .text(function(d) { return d.value; });
                    

            });
            function type(d) {
                d.value = +d.value; // coerce to number
                return d;

            }
       }
       $( document ).ready(function() {
            loadChart("USD");
            loadsymbols();
            $("#truncate").click(function(){
                $.get( "/service/truncate", function( data ) {
                    console.log(data+" keyspace truncated");
                });
            });
       });
