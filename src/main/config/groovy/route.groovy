package groovy

import org.apache.camel.builder.RouteBuilder
import jp.pgw.develop.swallow.tool.RouteDefinition
import jp.pgw.develop.swallow.tool.GenericRoute

beans {
    routes(GenericRoute, { RouteBuilder builder ->
        builder.onException(Exception.class).handled(true).to('jms:queue:err')
        builder.from("file:C:/dev/data/?fileName=input-\${date:now:yyyyMMddHHmmss}.txt").to('jms:queue:in')
        builder.from('jms:queue:in').to('jms:queue:out')
    } as RouteDefinition)
}
