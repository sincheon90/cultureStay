package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Checklist {
	
	int checklistID;
	int programNum;
	int reserNum; //예약번호
	String userid;
	
	// 게스트용 체크리스트
		private int petFriendly; //반려동물 유무
		private int allergyFriendly; // 알러지 유무
		private int chronicIllness; // 지병 유무
		private int foodPreference; // 식단 선호도
		private int privateTime; // 개인시간 원하는지 여부
		private int preferredProgramType; // 선호하는 홈스테이 유형
		private int languageSupport; // 언어대응 가능 여부
		private int smoking; // 흡연 유무
		
	String inputdate;          
}
