package com.javaclimb.music.interceptor;

//@Component
//public class VipInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//
//        String userId = request.getParameter("userId");
//        String validUntilKey = RedisPrefixUtils.generatorVipValidUntilKey(userId);
//        //vip信息
//        String validUntilData = stringRedisTemplate.opsForValue().get(validUntilKey);
//
//        response.setContentType("application/json;charset=utf-8");
//
//        if (StringUtils.isEmpty(validUntilData)) {
//            jsonObject.put(Consts.CODE, 0);
//            jsonObject.put(Consts.MSG, "vip已过期");
//            response.getWriter().write(jsonObject.toJSONString());
//
//            return false;
//        }
//
//        jsonObject.put(Consts.CODE, 1);
//        jsonObject.put(Consts.MSG, "vip信息返回成功");
//        jsonObject.put("data", validUntilData);
//        response.getWriter().write(jsonObject.toJSONString());
//
//        return true;
//    }
//}
