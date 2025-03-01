/*
 * Copyright Terracotta, Inc.
 * Copyright IBM Corp. 2024, 2025
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terracotta.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Properties;
import org.terracotta.connection.entity.EntityRef;
import org.terracotta.exception.EntityNotFoundException;
import org.terracotta.exception.EntityNotProvidedException;
import org.terracotta.exception.EntityVersionMismatchException;

/**
 *
 */
public class DiagnosticsFactory {

  public static String REQUEST_TIMEOUT = "request.timeout";
  public static String REQUEST_TIMEOUT_MESSAGE = "request.timeoutMessage";

  public static Diagnostics connect(InetSocketAddress server, Properties props) throws ConnectionException {
    if (props == null) {
      props = new Properties();
    }
    props.setProperty(ConnectionPropertyNames.CONNECTION_TYPE, "diagnostic");
    // try connection direct and only once
    props.setProperty(ConnectionPropertyNames.CONNECTION_TIMEOUT, "-1");
    Connection connection = ConnectionFactory.connect(Collections.singleton(server), props);
    try {
      EntityRef<Diagnostics, Object, DiagnosticsConfig> d = connection.getEntityRef(Diagnostics.class, 1L, "root");
      Diagnostics handle = d.fetchEntity(new DiagnosticsConfig(props, ()->{
        try {
          connection.close();
        } catch (IOException ioe) {
          throw new RuntimeException(ioe);
        }
      }));
      return handle;
    } catch (EntityNotProvidedException | EntityNotFoundException | EntityVersionMismatchException e) {
      try {
        connection.close();
      } catch (IOException c) {
        throw new RuntimeException(c);
      }
      throw new ConnectionException(e);
    }
  };
}
