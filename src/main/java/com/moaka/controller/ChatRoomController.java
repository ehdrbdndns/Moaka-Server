package com.moaka.controller;

import com.moaka.dto.Alarm;
import com.moaka.dto.Chat;
import com.moaka.mapper.AlarmMapper;
import com.moaka.mapper.LikeMapper;
import com.moaka.service.AlarmService;
import com.moaka.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ChatRoomController {

    @Autowired
    ChatService chatService;

    @Autowired
    LikeMapper likeMapper;

    @Autowired
    AlarmService alarmService;

    private final RabbitTemplate template;

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";

    @MessageMapping("chat.message.{chatRoomId}")
    public void send(Chat chat, @DestinationVariable String chatRoomId) {

        chat.setRegdate(getTodayDate());

        chatService.insertChat(chat);

        template.convertAndSend("amq.topic", "room." + chatRoomId, chat);
    }

    @MessageMapping("chat.deleteLike.{chatRoomId}")
    public void deleteLike(Chat chat, @DestinationVariable String chatRoomId) {
        chat.setRegdate(getToday());

        likeMapper.deleteChatLike(chat.getLike_no());

        template.convertAndSend("amq.topic", "room." + chatRoomId, chat);
    }

    @MessageMapping("chat.insertLike.{chatRoomId}")
    public void insertLike(Chat chat, @DestinationVariable String chatRoomId) {
        chat.setRegdate(getToday());

        likeMapper.insertChatLike(chat);

        template.convertAndSend("amq.topic", "room." + chatRoomId, chat);
    }

    @MessageMapping("chat.alarm.{user_no}")
    public void insertAlarm(Alarm alarm, @DestinationVariable int user_no) {
        alarm.setRegdate(getTodayDate());
        
        alarmService.insertAlarm(alarm);
        template.convertAndSend("amq.topic", "room.user_no" + user_no, alarm);
    }

    //receive()는 단순히 큐에 들어온 메세지를 소비만 한다. (현재는 디버그용도)
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(Chat chat) {

        System.out.println("received : " + chat.getContent());
    }

    public String getToday(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public String getTodayDate() {
        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
        SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fourteen_format.format(date_now); // 14자리 포멧으로 출력한다
    }
}
