package org.zhxie.sprintpoker.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticket_record")
@Data
public class TicketRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ticketNum;
    private String title;
    /**
     * 不能用desc， 它是保留关键字
     */
    private String description;
    private Double storyPoint;
    private String creator;

    @CreatedDate
    private Long createTime;
    @LastModifiedDate
    private Long updateTime;

}
