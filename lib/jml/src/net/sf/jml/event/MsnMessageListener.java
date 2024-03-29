/*
 * Copyright 2004-2005 the original author or authors.
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
package net.sf.jml.event;

import net.sf.jml.MsnContact;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnSwitchboard;
import net.sf.jml.message.*;
import net.sf.jml.message.p2p.MsnP2PMessage;

/**
 * Message listener.
 * 
 * @author Roger Chen
 * @author Angel Barragán Chacón
 * @author Damian Minkov
 */
public interface MsnMessageListener {

    /**
     * Received text message.
     * 
     * @param switchboard
     * 		MsnSwitchboard
     * @param message
     * 		received message
     * @param contact
     * 		the user who sent this message
     */
    public void instantMessageReceived(MsnSwitchboard switchboard,
                                       MsnInstantMessage message, 
                                       MsnContact contact);
    
    /**
     * Received offline text message.
     * 
     * @param body of message
     * @param contentType of message
     * @param encoding of message
     * @param contact the user who sent this message
     */
    public void offlineMessageReceived(String body,
                                       String contentType, 
                                       String encoding,
                                       MsnContact contact);

    /**
     * Received control message, such as typing text or recording voice clip.
     * 
     * @param switchboard
     * 		MsnSwitchboard
     * @param message
     *      control message
     * @param contact
     *      the user who sent this message
     */
    public void controlMessageReceived(MsnSwitchboard switchboard,
                                       MsnControlMessage message, 
                                       MsnContact contact);

    /**
     * Received system message. System message is sent by
     * NS server, so only MsnMessenger here.
     * 
     * @param messenger
     * 		MsnMessenger
     * @param message
     *      received message
     */
    public void systemMessageReceived(MsnMessenger messenger,
                                      MsnSystemMessage message);

    /**
     * Received datacast message.
     * 
     * @param switchboard
     * 		MsnSwitchboard
     * @param message
     *      received message
     * @param contact
     *      the user who sent this message
     */
    public void datacastMessageReceived(MsnSwitchboard switchboard,
                                        MsnDatacastMessage message, 
                                        MsnContact contact);

    /**
     * Received unknown message. Maybe because of this library
     * not support this message.
     * 
     * @param switchboard
     * 		MsnSwitchboard
     * @param message
     *      received message
     * @param contact
     *      the user who sent this message
     */
    public void unknownMessageReceived(MsnSwitchboard switchboard,
                                       MsnUnknownMessage message, 
                                       MsnContact contact);
    
    /**
     * Received P2P message. Maybe because we are retrieving a MsnObject.
     * 
     * @param switchboard MsnSwitchboard.
     * @param message Received message.
     * @param contact Contact that sended the message.
     */
    public void p2pMessageReceived(MsnSwitchboard switchboard,
    		                       MsnP2PMessage message,
    		                       MsnContact contact);
}