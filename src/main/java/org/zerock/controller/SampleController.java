package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/*
    Controller의 return 타입
    -> String : jsp파일의 경로와 파일이름
        => redirect:(리다이렉트 방식) / forward:(포워드 방식)
    -> void : 호출하는 URL과 동일한 이름의 jsp
    -> VO, DTO : JSON 타입의 데이터를 만들어서 반환
    -> ResponseEntity : response 할 때 Http 헤더 정보와 내용을 가공
    -> Model, ModelAndView : Model로 데이터를 반환하거나 화면까지 같이 지정하는 경우
    -> HttpHeaders : 응답에 내용 없이 Http 헤더 메시지만 전달하는 용도
 */
// @RequestMapping은 현재 클래스의 모든 메서드들의 기본적인 URL경로가 됨
// @Log4j는 Log4j 라이브러리 활용 / @Log는 java.util.Logger 이용
@Controller
@RequestMapping("/sample/*")    // ex) "/sample/aa", "/sample/bb" ...
@Log4j
public class SampleController {

    @RequestMapping("")
    public String basic() {
        log.info("basic................");
        return "home";
    }

    // @RequestMapping -> GET, POST 방식 모두 지원
    @RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
    public void basicGet() {
        log.info("basic get...............");
    }

    // @GetMapping -> GET 방식만 처리
    @GetMapping("/basicOnlyGet")
    public void basicGet2() {
        log.info("basic get only get...........");
    }

    // SampleDTO를 파라미터로 사용하면 자동적으로 setter 메서드가 동작하면서 파라미터를 수집
    // int형인 age가 자동으로 숫자로 변환됨
    @GetMapping("/ex01")
    public String ex01(SampleDTO dto) {
        log.info("" + dto);

        return "ex01";
    }

    // @RequestParam은 파라미터로 사용된 변수의 이름과 전달되는 파라미터의 이름이 다른 경우 유용하게 사용됨
    @GetMapping("/ex02")
    public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
        log.info("name : " + name);
        log.info("age : " + age);

        return "ex02";
    }

    // 파라미터가 여러개 전달되는 경우
    // List 사용
    @GetMapping("/ex02List")
    public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
        log.info("ids : " + ids);

        return "ex02List";
    }

    // 배열 사용
    @GetMapping("/ex02Array")
    public String ex02Array(@RequestParam("ids") String[] ids) {
        log.info("array ids : " + Arrays.toString(ids));

        return "ex02Array";
    }

    // 같은 타입의 객체를 여러개 전달 -> SampleDTOList 클래스 이용
    @GetMapping("/ex02Bean")
    public String ex02Bean(SampleDTOList list) {
        log.info("list dtos : " + list);

        return "ex02Bean";
    }

    /*
    // @InitBinder : 파라미터를 변환해서 처리
    // "2018-01-01"과 같이 문자열로 전달된 데이터를 java.util.Date 타입으로 변환
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
    }
     */

    // 파라미터 TodoDTO 형태
    @GetMapping("/ex03")
    public String ex03(TodoDTO todo) {
        log.info("todo : " + todo);
        return "ex03";
    }

    /*
        Model : jsp에 컨트롤러에서 생성된 데이터를 담아서 전달하는 역할
            -> view로 전달해야 하는 데이터를 담을 수 있음
            -> request.setAttribute()와 유사한 역할
        Model을 사용해야 하는 경우는 주로 Controller에 전달된 데이터를 이용해서 추가적인 데이터를 가져와야하는 상황
            -> 리스트 페이지 번호를 파라미터로 전달받고, 실제 데이터를 View로 전달해야 하는 경우
            -> 파라미터들에 대한 처리 후 결과를 전달해야 하는 경우
     */
    @RequestMapping("/")
    public String home(Model model) {

        model.addAttribute("serverTime", new java.util.Date());

        return "home";
    }

    // 기본 자료형은 파라미터로 선언하더라도 화면까지 전달되지 않음
    // @ModelAttribute가 걸린 파라미터는 타입에 관계없이 무조건 model에 담아서 전달됨
    @GetMapping("/ex04")
    public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
        log.info("dto : " + dto);
        log.info("page : " + page);
        return "/sample/ex04";
    }

    // Controller의 리턴 타입이 void인 경우
    // -> 해당 URL의 경로 그대로 jsp파일의 이름으로 사용
    // "/WEB-INF/views/sample/ex05.jsp"
    @GetMapping("/ex05")
    public void ex05() {
        log.info("/ex05.............");
    }

    // Controller의 리턴 타입이 VO, DTO인 경우
    // 스프링 MVC는 자동으로 브라우저에 JSON 타입으로 객체를 변환해서 전달함
    @GetMapping("/ex06")
    public @ResponseBody SampleDTO ex06() {
        log.info("/ex06..............");
        SampleDTO dto = new SampleDTO();
        dto.setAge(10);
        dto.setName("Ray");

        return dto;
    }

    // Controller의 리턴 타입이 ResponseEntity인 경우
    // ResponseEntity는 HttpHeaders 객체를 같이 전달할 수 있고, 이를 통해서 원하는 HTTP 헤더 메시지를 가공할 수 있음
    @GetMapping("/ex07")
    public ResponseEntity<String> ex07() {
        log.info("/ex07.............");

        // {"name" : "Ray"}
        String msg = "{\"name\" : \"Ray\"}";

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json;charset=UTF-8");

        // JSON 타입이라는 헤더 메시지와 200 OK라는 상태코드를 전송
        return new ResponseEntity<>(msg, header, HttpStatus.OK);
    }

    // 파일을 업로드할 화면
    @GetMapping("/exUpload")
    public void exUpload() {
        log.info("/exUpload...........");
    }

    // 업로드한 파일 처리
    // 전달되는 파라미터가 동일한 이름으로 여러개 존재하면 배열로 처리 가능하므로 파라미터를 MultipartFile의 배열로 작성
    @PostMapping("/exUploadPost")
    public void exUploadPost(ArrayList<MultipartFile> files) {
        files.forEach(file -> {
            log.info("-----------------------");
            log.info("name : " + file.getOriginalFilename());
            log.info("size : " + file.getSize());
        });
    }

}
