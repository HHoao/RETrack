package com.rare_earth_track.portal.service;

import com.rare_earth_track.mgb.model.RetFactory;
import com.rare_earth_track.portal.bean.MailType;

/**
 * The interface Ret mail service.
 *
 * @author hhoa
 * @date 2022 /5/15
 */
public interface RetMailService {
    /**
     * 获取消息
     *
     * @param to   接收方
     * @param type the type
     * @return 消息 auth code
     */
    String getMessage(String to, MailType type);

    /**
     * Send user register mail.
     *
     * @param to the to
     * @return string authcode
     */
    String sendUserRegisterMail(String to);

    /**
     * Send bind mail string.
     *
     * @param to the to
     * @return the string
     */
    String sendBindMail(String to);

    /**
     * Send factory invitation.
     *
     * @param to      the to
     * @param factory the factory
     */
    void sendFactoryInvitation(String to, RetFactory factory);

    /**
     * 是否存在消息
     *
     * @param to   接收方
     * @param type the type
     * @return boolean boolean
     */
    boolean existMessage(String to, MailType type);

    /**
     * 验证消息
     *
     * @param to   接收方
     * @param code 消息
     * @param type the type
     * @return boolean boolean
     */
    boolean validateMessage(String to, String code, MailType type);
}
