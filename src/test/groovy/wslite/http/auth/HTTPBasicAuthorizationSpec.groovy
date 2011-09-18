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
package wslite.http.auth

import spock.lang.*

class HTTPBasicAuthorizationSpec extends Specification {

    def "sets authorization header"() {
        given:
        def conn = new Expando()
        conn.addRequestProperty = { k, v ->
            conn.getRequestProperty = { k2 -> v }
        }

        expect:
        def auth = new HTTPBasicAuthorization(username, password)
        auth.authorize(conn)
        encodedAuthorization == conn.getRequestProperty("Authorization")

        where:
        username        | password              | encodedAuthorization
        "Aladdin"       | "open sesame"         | "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ=="
        "homers"        | "mr.simpson"          | "Basic aG9tZXJzOm1yLnNpbXBzb24="
    }

}
