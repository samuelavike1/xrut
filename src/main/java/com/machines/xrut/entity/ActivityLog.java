package com.machines.xrut.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "activity_logs")
public class ActivityLog {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String username;
  private String activity;

  @CreationTimestamp
  @Column(name = "performed_at", updatable = false)
  private Instant performedAt;

  }