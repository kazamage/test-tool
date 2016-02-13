package groovy

import jp.pgw.develop.swallow.tool.GenericRoute
import jp.pgw.develop.swallow.tool.RouteDefinition

import org.apache.camel.builder.RouteBuilder

beans {
  routes(GenericRoute, { RouteBuilder builder ->
    builder
      .onException(Exception.class)
      .handled(true)
      .to('jms:queue:err')
//    builder
//      .from('file:C:/dev/data/?fileName=input-\${date:now:yyyyMMddHHmmss}.txt')
//      .routeId('route1')
//      .to('jms:queue:in?includeSentJMSMessageID=true')
//      .log('\${in.header.JMSMessageId}')
    builder
      .from('file:C:/dev/data/?fileName=input.txt')
      .routeId('fileToIn')
      .to('jms:queue:in?includeSentJMSMessageID=true')
      .log('to in  : \${in.header.JMSMessageId}')
    builder
      .from('jms:queue:in')
      .routeId('inToOut')
      .log('from in: \${in.header.JMSMessageId}')
      .to('jms:queue:out?includeSentJMSMessageID=true')
      .log('to out : \${in.header.JMSMessageId}')
  } as RouteDefinition)
}
