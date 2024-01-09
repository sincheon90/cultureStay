package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramTag {
	//홈스테이 태그, 특징, 서치필터
	//1,0으로 특징 구분
	private int programNum;
	private int apartment;
	private int detached; //단독주택

	// 홈스테이 테마 태그
	private int active; // 활동적
	private int creative; // 창의적
	private int healing; // 힐링
	private int traditional; // 전통
	private int cookingExperience; // 요리체험
	private int scenicView; // 뷰
	private int countryside; // 시골
	private int city; // 도시
	private int festival; // 축제
	private int drive; // 드라이브
	private int socializing; // 친목
	private int tranquil; // 한적한

	// 호스트용 체크리스트
	private int separateBathroom; // 화장실과 욕실 분리 여부
	private int bathtub; // 욕조 유무
	private int wifi; // 와이파이 유무
	private int transformer; // 변압기 유무
	private int pajamasProvided; // 파자마(잠옷) 제공 여부
	private int hairdryer; // 헤어드라이기 유무
	private int basicToiletriesProvided; // 기본 세면도구 제공 여부
	private int petFriendly; // 반려동물 동반 가능 여부
	private int roomAlone;
	private int langJapanese;
	private int langEnglish;
	private int langKorean;

	
}
