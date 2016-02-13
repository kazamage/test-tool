package groovy

import org.apache.camel.component.jms.JmsComponent
import org.apache.camel.component.jms.JmsConfiguration
import org.apache.geronimo.transaction.manager.TransactionManagerImpl
import org.springframework.transaction.jta.JtaTransactionManager

import com.ibm.mq.jms.MQConnectionFactory
import com.ibm.msg.client.wmq.WMQConstants

beans {
  connectionFactory(MQConnectionFactory) {
    queueManager = environment.getRequiredProperty('wmq.queueManager')
    channel = environment.getRequiredProperty('wmq.channel')
    hostName = environment.getRequiredProperty('wmq.hostName')
    port = environment.getRequiredProperty('wmq.port')
    transportType = WMQConstants.WMQ_CM_CLIENT
  }
  transactionManager(JtaTransactionManager) {
    TransactionManagerImpl txManager = new TransactionManagerImpl()
    transactionManager = txManager
    userTransaction = txManager
  }
  jmsConfig(JmsConfiguration) {
    connectionFactory = connectionFactory
    transactionManager = transactionManager
    transacted = true
    cacheLevelName = environment.getRequiredProperty('jms.cacheLevelName')
    transactionTimeout = environment.getRequiredProperty('jms.transactionTimeout')
  }
  jmsComponent(JmsComponent) {
    configuration = jmsConfig
  }
}
