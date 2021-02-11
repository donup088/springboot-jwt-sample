# springboot-jwt-sample
Springboot&amp;Jwt sample code

---
### AuthController
- /api/authenticate 경로로 로그인을 진행하고 로그인이 되었다면 토큰을 발급해준다.
- UsernamePasswordAuthenticationToken에 username과 password 이렇게 인자 2개를 주면
 인증전 UsernamePasswordAuthenticationToken 을 만들 수 있고 authrities 인자 하나를 추가
  하여 3개의 인자를 전달하면 인증 된 UsernamePasswordAuthenticationToken을 얻을 수 있다.
- authenticationManagerBuilder를 주입받아서 authenticate 를 하기위해 UsernamePasswordAuthenticationToken를 전달하여 인증을 진행한다.
- authenticate는 provider를 직접 만들고 SecurityConfig에 직접 추가하여 구현할 수도 있지만 구현되어있는 provider를 사용하였다.
- authenticate가 진행될 때 CustomUserDetailService의 loadUserByUsername 메소드가 실행되고 생성된 Authentication객체를 SecurityContextHolder에 저장한다.
- 인증이 되었다면 jwt 토큰을 만들어서 응답 header 와 body 부분에 추가시킨다.

### 인증이 필요한 요청을 할 경우
- 클라이언트에서 요청 헤더에 토큰을 담아서 준다.
- jwtFilter를 통해서 토큰의 유효성을 검증하고 인증된 사용자이면 SecurityContextHolder에 Authentication을 저장해준다.
- 토큰이 잘못되었다면 InsufficientAuthenticationException이 발생한다.
- Exception은 JwtAuthenticationEntryPoint에서 잡아서 에러를 반환해준다.

### 로그인 실패 처리
- 로그인이 실패 했다면 아이디또는 비밀번호가 잘못 된 경우 BadCredentialsException이 발생하도록 하였다.
- CustomUserDetailService에서 아이디가 없을 경우 UsernameNotFoundException이 반환하도록 하였지만 authenticate 안에서 잡히고 BadCredentialsException로 예외를 던지도록 구현되어있다. 
- Exception은 JwtAuthenticationEntryPoint에서 잡아서 에러를 반환해준다.

### 권한이 없을 경우
- jwt 토큰을 파싱하여 권한이 없을 경우 JwtAccessDeniedHandler에서 예외처리를 담당한다.


