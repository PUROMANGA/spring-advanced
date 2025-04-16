# SPRING ADVANCED

1. [로그인 할 때 토큰 정의 방식]
package org.example.expert.domain.auth.service; 
의 signup 메소드에서 signupRequest.getUserRole()을 해줘서 그걸 컨버터해서 나오는 걸 확인했습니다.

2. [해결 방안], [해결 완료]
일단 제가 공부한 바에 의하면, 혹여나 role을 직접 입력하게끔 만들어버리면 요청하는 쪽에서 role을 자유롭게 선택 가능하기 때문에
직접 USER라는 롤을 주는 메소드로 바꾸었고, ADMIN은 ADMIN으로 가입하는 메소드를 따로 만들 예정입니다.
(공부 양이 많아서 예정만 있는 걸로...)
