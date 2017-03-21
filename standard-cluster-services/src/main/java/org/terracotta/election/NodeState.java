package org.terracotta.election;

import com.tc.classloader.CommonComponent;

@CommonComponent
public enum NodeState {
  ACTIVE_COORDINATOR,
  RECOVERING,
  PASSIVE_UNINITIALIZED,
  PASSIVE_STANDBY,
  START_STATE,
  STOP_STATE
}
