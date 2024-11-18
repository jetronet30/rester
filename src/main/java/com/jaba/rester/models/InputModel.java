package com.jaba.rester.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    public String inputUrl;
    public String channelName;
    public boolean isOut;
    public boolean isActiv;
    public Long taskPid;
    public String localLogoUrl;
    public String outLogoUrl;
    public boolean isSelected;
    public String channelUrl;
    public Long indexer;
}
