package restful.dns

import com.budjb.dns.AuthoritativeLookupService
import com.budjb.dns.datasource.MongoDbDatasource
import com.budjb.dns.server.impl.DnsServerImpl
import com.budjb.dns.server.impl.DnsServerComponentFactoryImpl
import com.budjb.dns.util.ByteUtil
import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration

class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application)
    }

    Closure doWithSpring() {
        { ->
            // DNS server bean
            dnsNetworkServer(DnsServerImpl)

            // Byte conversion utility
            byteUtil(ByteUtil)

            // DNS server component factory
            dnsServerComponentFactory(DnsServerComponentFactoryImpl)

            mongoDbDatasource(MongoDbDatasource)

            authoritativeLookupService(AuthoritativeLookupService)
        }
    }

    void doWithApplicationContext() {
        applicationContext.getBean('mongoDbDatasource').connect()

        applicationContext.getBean('dnsNetworkServer').start()
    }

    void onShutdown(Map<String, Object> event) {
        DnsServerImpl dnsNetworkServer = applicationContext.getBean('dnsNetworkServer')

        dnsNetworkServer.stop()
    }
}