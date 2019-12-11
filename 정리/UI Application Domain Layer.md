### Spring  시작하기

- 프로젝트 설정(Dependencies)
  - Spring Web Starter
    - Rest API 사용
  - Spring Boot DevTools
    - 개발의 원활한 지원
  - LomBok
    - 자동완성 지원

### 프로젝트 구조

- UI Layer
    - 생성자와 가장 가까운 Layer로서, controller에 관련된 정보가 있음
- Application Layer
    - Controller 내에 복잡한 로직들을 단순화 하기 위함
    - Service와 같은 비즈니스 로직 구현이 이루어지는 곳
- Domain Layer
    - DTO(Member) 및 Repository 관리
    - Repository
        - Member 및 Member의 Collection을 관리하고, Member의 필요정보를 가지고 있음
        - Ex. findAll(모든 리스트) || findById(상세) 
    
### 멀티 프로젝트 구조로 만들기

1. api 패키지 생성

2. src 및 build.gradle 해당 패키지(api)에 삽입

3. api안에 UI Layer(interfaces) 패키지 생성

   1. UI Layer를 생성(사용자와 가장 가까운 Layer)
   2. controller의 정보가 있음.
      1. @RestController
      2. @GetMapping("/")으로 url 접근

4. api 안에 Application Layer(Application) 패키지 생성

   1. 복잡한 로직을 단순화하기 위함

      1. Controller의 복잡성을 단순화 시킴

   2. Service가 들어감
      ```
      [예]
      //id를 줄게 레스토랑의 기본정보와 메뉴정보를 달라.
      
      [복잡한 controller]
      //controller에서 repository를 활용해 정보 불러옴
      Restaurant restaurant = restaurantRepository.findById(id);
      List<MenuItem> menuItems = menuItemRepository.findAllByRestaurentId(id);
      restaurant.setMenuItem(menuItems);
      
      [Application Layer 사용 - 기대함]
      //service활용
      Restaurant restaurant = restaurentService.getRestaurentById(id);
      ```

   4. @Before

      1. 모든 테스트가 실행되기 전에 @Before 붙여있는 메서드를 한번씩 실행됨
      2. @Test가 두개 일 경우 @Before는 총 두번 실행됨

5. api 안에 Domain Layer(domain) 생성

   1. 도메인 모델 만듬(DTO(Restaurant))

   2. repository 생성

   3. reapository를 이용해 중복 코드 수정

      1. DTO의 저장소

      2. 기대하는 것

         1. Controller 부분을 respository로 옮겨 controller는 사용자와 내부의 비즈니스 로직과 상관 없고 징검다리 역할만 할 수 있게끔 함
         2. DTO의 관리는 도메인 모델쪽에 위치하도록 함

         ```
         [Controller]
         List<Restaurant> restaurants = new ArrayList<>();
         restaurants.add(new Restaurant(1004L,"Bob zip", "Seoul"));
         restaurants.add(new Restaurant(2020L,"Kimbob heaven", "Seoul"));
         
         이런 값들을 repository에 넣은 후 controller에서는 밑에것만 호출
         
         List<Restaurant> restaurants = repository.findAll();
         ```



### TDD

- 목표 주도 개발

- 사용자 중심 개발

- 인터페이스 중심 개발

- 목적

  - 정상적인 작동 및 깔끔한 코드

- 핵심 아이디어

  - Test First!

- TDD 흐름

  - Red : 에러
  - Green : 유일한 값에 대한 성공
  - Refactoring : 유일하지 않은 값에 대한 성공(변수 파라미터 대입 시)

- TDD 명령어

  ```
   mvc.perform(get("/restaurants"))
                  .andExpect(status().isOk())      //요청한 것이 성공적으로 나오나 확인
                  .andExpect(content().string(
                          containsString("\"id\":1004")
                  ))	//해당 url에서 호출되는 값(json)에 id:1004가 포함되어 있나 확인
                  .andExpect(content().string(
                          containsString("\"name\":\"Bob zip\"")
                  ));
  ```

  



### Rest API

- 웹과 모바일 등 다양한 환경을 지원해야 함
  - 서로 다른 Front-end를 둬야함
  - 이들이 공통으로 사용하는 Back-end을 둠
  - 이 때의 Back-end는 **Rest API**를 활용
- REST
  - 표현 상태를 전달
- Resource
  - 리소스에 대한 처리 
  - CRUD를 활용
  - POST(create), GET(read), PUT/PATCH(update), DELETE(delete)
  - 리소스는 Collection과 Member로 나눔
    - Collection : Read(List), Create
    - Member : Read(Detail), Update, Delete
- 예(Restaurant)
  - Collection : http://host/restaurants
  - Member : http://host/restaurants/{id}
  - JSON
    - 결과를 얻거나 정보를 줄 때 사용
    - { "id" : 1, "name" : "가게", "address" : "수원"}
      - 출력되는 결과로 Front-end와 협업 진행함
- APIs
  - 가게 목록 : GET /restaurants에 접근
  - 가게 상세 : GET /restaurants/{id}로 상세 정보 얻음
    - 결과 : { "id" : 1, "name" : "가게", "address" : "수원"}
  - 가게 추가 : POST /restaurants
  - 가게 수정 : PATCH /restaurants/{id}
  - 가게 삭제 : DELETE /restaurants/{id}



### 의존성 주입

- 의존 관계 : 둘 이상의 객체가 서로 협의

- 예

  - A는 B에 의존
    - A는 B를 사용
    - B의 변화가 A에 영향

- 현재 소스

  - Controller는 Repository에 의존
  - Repository 생성은 Controller가 책임을 가짐
  - 즉, Controller에 Repository를 연결
  - 하지만 Repository 생성을 Spring IoC Container가 대신 해줌
    - 어노테이션을 사용해 스프링이 관리
      - @Component
      - @Autowired

- 도움이 되는 점

  - 사용해야할 객체를 다양하게 변경 가능
    - 즉, 인터페이스(추상)와 구현체로 인해 인터페이스에게 의존성 주입을 하게 되면 사용해야할 구현체를 다양하게 변경 가능함.
    - 그로인해, 인터페이스를 의존하고 있는 객체는 구현체를 변경해도(메소드를 제외한) 영향이 끼치지 않음.

- Test에서는 직접 의존성 주입해줘야 함.

  - @SpyBean으로 직접 의존성 주입이 가능

    - ```
      @SpyBean(RestaurantRepositoryImpl.class) //어떤 구현체를 쓰겠다라는 것을 정의해줘야 함
      ```

