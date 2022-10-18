package com.example.sparta.entity;

import com.example.sparta.controller.dto.MemberRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Timestamped {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;


    public Member(MemberRequestDto memberRequestDto){
        this.name = memberRequestDto.getUsername();
        this.email = memberRequestDto.getEmail();
        this.password = memberRequestDto.getPassword();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
//            return false;
//        }
//        Member member = (Member) o;
//        return id != null && Objects.equals(id, member.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//
//    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
//        return passwordEncoder.matches(password, this.password);
//    }
}
