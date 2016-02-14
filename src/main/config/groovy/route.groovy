package groovy

import jp.pgw.develop.swallow.tool.GenericRoute
import jp.pgw.develop.swallow.tool.RouteDefinition

import org.apache.camel.builder.RouteBuilder
import org.apache.camel.Exchange
import org.apache.camel.processor.aggregate.AggregationStrategy

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
      .from('file:C:/dev/data/?recursive=true')
      .routeId('fileToIn')
      .log('\ninput body:\n\${body}')
      .to('jms:queue:in?includeSentJMSMessageID=true')
      .setHeader('inId', builder.simple('\${header.JMSMessageId}'))
      .pollEnrich("jms:queue:out?includeSentJMSMessageID=true", { Exchange original, Exchange resource ->
          original.getIn().setBody(resource.getIn().getBody())
          original.getIn().headers.put('outId', resource.getIn().getHeader("JMSMessageId"))
          return original
      } as AggregationStrategy)
      .pollEnrich('file:C:/dev/diff/?fileName=\${header.CamelFileName}&readLockMarkerFile=false', { Exchange original, Exchange resource ->
          String expected = resource.getIn().getBody(String)
          String actual = original.getIn().getBody(String)
          original.getIn().setHeader("expected", expected)
          original.getIn().setHeader("compResult", java.util.Objects.equals(expected, actual))
          return original
      } as AggregationStrategy)
      .log('\ninput id:\n\${header.inId}\noutput id:\n\${header.outId}\nexpected body:\n\${header.expected}\nactual body:\n\${body}\ncompResult:\n\${header.compResult}')
    builder
      .from('jms:queue:in')
      .routeId('inToOut')
      .log('from in: \${header.JMSMessageId}')
      .to('jms:queue:out?includeSentJMSMessageID=true')
      .log('to out : \${header.JMSMessageId}')
  } as RouteDefinition)
}
