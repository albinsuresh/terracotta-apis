package org.terracotta.election;

import com.tc.classloader.CommonComponent;

@CommonComponent
public interface TieBreakerStrategy {
  
  enum Result {
    
    /* The result if the requesting node has won the tie breaking attempt */
    WON,
    
    /**
     *  The result if the requesting node has NOT won the the tie breaking
     *  attempt AND this tie-breaker is aware that another node 
     *  has already WON */
    LOST,

    /**
     *  The result if the tie-breaker is not able to break the tie OR 
     *  if the requesting node has NOT won the the tie breaking
     *  attempt and this tie-breaker is not aware that another node 
     *  has already WON */
    DUBIOUS
  }

  /**
   *
   * @param groupName The group that the this server is a member of
   * @param incarnation Current incarnation number
   * @param state Current state of the server
   * @return
   */
  Result breakTie(String groupName, int incarnation, NodeState state);
}
