# SpringBootProj-1
스프링부트 관련 공부용 쇼핑몰 프로젝트입니다.

### 라이브러리
- org.springframework.boot:2.7.0
- spring-boot-starter-web
- spring-boot-starter-test
- spring-boot-starter-thymeleaf
- spring-boot-starter-data-jpa
- spring-boot-devtools
- org.projectlombok:lombok
- com.h2database:h2

### 도메인 분석 설계

#### 요구사항 분석
##### 기능목록
- 회원기능
  - 회원등록
  - 회원조회
- 상품기능
  - 상품등록
  - 상품수정
  - 상품조회
- 주문기능
  - 상품주문
  - 주문 내역 조회
  - 주문 취소
- 기타 요구사항
  - 상품은 재고 관리가 가능하다.
  - 상품의 종류는 도서, 음반, 영화가 있다.
  - 상품을 카테고리로 구분할 수 있다.
  - 상품 주문시 배송 정보를 입력할 수 있다.

##### 도메인 모델 설계
<img src="/src/main/resources/static/images/domainDesign.png" width="500px;" height="250px;">

##### 테이블 설계
ITEM 테이블을 싱글 테이블 전략 사용 : 상품 별로 테이블을 나눠도 되지만 토이 프로젝트를 감안해서 싱글 테이블 전략 사용
<img src="/src/main/resources/static/images/tableDesign.png" width="500px;" height="250px;">
