/* Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wslite.soap

import spock.lang.*

class SOAPClientIntegrationSpec extends Specification {

    def "accessing a public SOAP service"() {
        given:
        def soapClient = new SOAPClient(serviceURL:'http://www.holidaywebservice.com/Holidays/US/Dates/USHolidayDates.asmx')

        when:
        def resp = soapClient.send(SOAPAction:'http://www.27seconds.com/Holidays/US/Dates/GetMartinLutherKingDay') {
            body {
                GetMartinLutherKingDay('xmlns':'http://www.27seconds.com/Holidays/US/Dates/') {
                    year(2011)
                }
            }
        }

        then:
        "2011-01-15T00:00:00" == resp.Envelope.Body.GetMartinLutherKingDayResponse.GetMartinLutherKingDayResult.text()
        200 == resp.status
        "OK" == resp.statusMessage
        "ASP.NET" == resp.headers['X-Powered-By']

    }

}