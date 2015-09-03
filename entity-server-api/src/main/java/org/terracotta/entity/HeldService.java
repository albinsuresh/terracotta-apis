/*
 *
 *  The contents of this file are subject to the Terracotta Public License Version
 *  2.0 (the "License"); You may not use this file except in compliance with the
 *  License. You may obtain a copy of the License at
 *
 *  http://terracotta.org/legal/terracotta-public-license.
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 *  the specific language governing rights and limitations under the License.
 *
 *  The Covered Software is Entity API.
 *
 *  The Initial Developer of the Covered Software is
 *  Terracotta, Inc., a Software AG company
 *
 */
package org.terracotta.entity;

/**
 *
 * @author mscott
 * @param <T> type of the held service
 */
public class HeldService<T> implements Service<T> {
  
  private final T held;

  public HeldService(T held) {
    this.held = held;
  }

  @Override
  public void initialize(ServiceConfiguration<? extends T> configuration) {
// do nothing
  }

  @Override
  public T get() {
    return held;
  }

  @Override
  public void destroy() {
// do nothing
  }
    
}