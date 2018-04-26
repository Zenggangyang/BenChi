package com.soowin.staremblem.ui.index.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hxt on 2018/3/16 0016.
 * 订单列表
 */

public class OrderListBean extends BaseBean {


    /**
     * data : [{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"北京南站","Destination":"故宫博物院","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-16 19:12","PassengerName":"测试多拉尔么好","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":2},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":2.4301855468888887,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9423,"UserID":7708,"OrderCode":"1523862785691","ProductID":5,"CreateTime":"2018-04-16T15:13:05.69","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":5,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"740\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":null,\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"北京南站\",\"Destination\":\"故宫博物院\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-16 19:12\",\"PassengerName\":\"测试多拉尔么好\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":null,\"CarTypeID\":0,\"OrderSource\":2}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-16T15:13:05.69","OrderUpdateTime":"2018-04-16T15:13:05.69","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"4705217965980312775","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":7,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.385488\",\"FromLatitude\":\"39.87128\",\"ToLongitude\":\"116.403414\",\"ToLatitude\":\"39.924091\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"故宫博物院","Destination":"北京南站","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-16 19:10","PassengerName":"测试哆啦额么么和","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":2},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":2.3968522135555554,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9422,"UserID":7708,"OrderCode":"1523862619629","ProductID":5,"CreateTime":"2018-04-16T15:10:19.63","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":5,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"740\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":null,\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"故宫博物院\",\"Destination\":\"北京南站\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-16 19:10\",\"PassengerName\":\"测试哆啦额么么和\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":null,\"CarTypeID\":0,\"OrderSource\":2}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-16T15:10:19.63","OrderUpdateTime":"2018-04-16T15:10:19.63","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5362626437604810690","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":7,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.403414\",\"FromLatitude\":\"39.924091\",\"ToLongitude\":\"116.385488\",\"ToLatitude\":\"39.87128\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"北京南站","Destination":"故宫博物院","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-16 19:6","PassengerName":"测试哆啦二萌","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":2},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":2.330185546888889,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9421,"UserID":7708,"OrderCode":"1523862401754","ProductID":5,"CreateTime":"2018-04-16T15:06:41.753","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":5,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"740\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":null,\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"北京南站\",\"Destination\":\"故宫博物院\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-16 19:6\",\"PassengerName\":\"测试哆啦二萌\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":null,\"CarTypeID\":0,\"OrderSource\":2}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-16T15:06:41.753","OrderUpdateTime":"2018-04-16T15:06:41.753","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5728293474150644321","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":7,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.385488\",\"FromLatitude\":\"39.87128\",\"ToLongitude\":\"116.403414\",\"ToLatitude\":\"39.924091\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"天安门","Destination":"北京站","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-16 15:46","PassengerName":"测试哆啦二萌","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":2},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-1.0031477864444445,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9404,"UserID":7708,"OrderCode":"1523850420707","ProductID":5,"CreateTime":"2018-04-16T11:47:00.707","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":5,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"740\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":null,\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"天安门\",\"Destination\":\"北京站\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-16 15:46\",\"PassengerName\":\"测试哆啦二萌\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":null,\"CarTypeID\":0,\"OrderSource\":2}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-16T11:47:00.707","OrderUpdateTime":"2018-04-16T11:47:00.707","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5613020975830643758","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":3,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.403963\",\"FromLatitude\":\"39.915119\",\"ToLongitude\":\"116.433547\",\"ToLatitude\":\"39.909462\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"北京站","Destination":"天安门","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-16 14:11","PassengerName":"测试","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":2},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-2.5864811197777775,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9401,"UserID":7708,"OrderCode":"1523844737879","ProductID":5,"CreateTime":"2018-04-16T10:12:17.88","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":5,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"740\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":null,\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"北京站\",\"Destination\":\"天安门\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-16 14:11\",\"PassengerName\":\"测试\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":null,\"CarTypeID\":0,\"OrderSource\":2}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-16T10:12:17.88","OrderUpdateTime":"2018-04-16T10:12:17.88","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5454822661795353186","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":5,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.433547\",\"FromLatitude\":\"39.909462\",\"ToLongitude\":\"116.403963\",\"ToLatitude\":\"39.915119\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":1,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"657","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":"MU5109","CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":null,"Destination":"故宫博物院","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-04-16 14:25:00","PassengerName":"测试哆啦E梦","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":"北京首都国际机场T2","CarTypeID":0,"OrderSource":2},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-2.3531477864444446,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9396,"UserID":7708,"OrderCode":"1523839955316","ProductID":1,"CreateTime":"2018-04-16T08:52:35.317","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":1,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"657\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":\"MU5109\",\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":\"故宫博物院\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-04-16 14:25:00\",\"PassengerName\":\"测试哆啦E梦\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":\"北京首都国际机场T2\",\"CarTypeID\":0,\"OrderSource\":2}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-16T08:52:35.317","OrderUpdateTime":"2018-04-16T08:52:35.317","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"4950870351792244112","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":28,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.599563\",\"FromLatitude\":\"40.085493\",\"ToLongitude\":\"116.403414\",\"ToLatitude\":\"39.924091\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":1,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"657","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":"MU5101","CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":null,"Destination":"故� ��博物院","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-14 21:51","PassengerName":"测试哆啦恶梦","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":"北京首都国际机场T2","CarTypeID":0,"OrderSource":2},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-42.91981445311111,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9371,"UserID":7708,"OrderCode":"1523699497598","ProductID":1,"CreateTime":"2018-04-14T17:51:37.597","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":1,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"657\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":\"MU5101\",\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":\"故宫博物院\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-14 21:51\",\"PassengerName\":\"测试哆啦恶梦\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":\"北京首都国际机场T2\",\"CarTypeID\":0,\"OrderSource\":2}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-14T17:51:37.597","OrderUpdateTime":"2018-04-14T17:51:37.597","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"4657627965491079584","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":28,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.599563\",\"FromLatitude\":\"40.085493\",\"ToLongitude\":\"116.403414\",\"ToLatitude\":\"39.924091\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":1,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"657","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":"MU5101","CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":null,"Destination":"故宫博物院","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-14 21:31","PassengerName":"测试哆啦二萌","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":"北京首都国际机场T2","CarTypeID":0,"OrderSource":2},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-43.253147786444444,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9370,"UserID":7708,"OrderCode":"1523698300504","ProductID":1,"CreateTime":"2018-04-14T17:31:40.503","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":1,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"657\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":\"MU5101\",\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":\"故宫博物院\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-14 21:31\",\"PassengerName\":\"测试哆啦二萌\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":\"北京首都国际机场T2\",\"CarTypeID\":0,\"OrderSource\":2}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-14T17:31:40.503","OrderUpdateTime":"2018-04-14T17:31:40.503","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5148870269589031813","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":28,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.599563\",\"FromLatitude\":\"40.085493\",\"ToLongitude\":\"116.403414\",\"ToLatitude\":\"39.924091\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"故宫博物院","Destination":"北京首都国际机场-t2航站楼","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-14 13:11","PassengerName":"安全大师的","PassengerMobile":"15532650303","Remark":"需英文司机;行李多需上门;需要女司机;","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":2},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-51.58648111977777,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9364,"UserID":7708,"OrderCode":"1523668324582","ProductID":5,"CreateTime":"2018-04-14T09:12:04.583","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":5,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"740\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":null,\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"故宫博物院\",\"Destination\":\"北京首都国际机场-t2航站楼\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-14 13:11\",\"PassengerName\":\"安全大师的\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"需英文司机;行李多需上门;需要女司机;\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":null,\"CarTypeID\":0,\"OrderSource\":2}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-14T09:12:04.583","OrderUpdateTime":"2018-04-14T09:12:04.583","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5193545505689239341","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":27,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.403414\",\"FromLatitude\":\"39.924091\",\"ToLongitude\":\"116.599563\",\"ToLatitude\":\"40.085493\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":2,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"658","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":"MU5183","CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"北京首都国际机场-t2航站楼","Destination":null,"ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-04-14T07:25:00","PassengerName":"测试哆啦二梦","PassengerMobile":"15532650303","Remark":"需英文客服;需英文司机;行李多需上门;需要女司机;","Driver":null,"PaymentType":"","Address":"上海浦东国际机场T1","CarTypeID":0,"OrderSource":0},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-57.353147786444445,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9328,"UserID":7708,"OrderCode":"1523600418895","ProductID":2,"CreateTime":"2018-04-13T14:20:18.893","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":2,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"658\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":\"MU5183\",\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"北京首都国际机场-t2航站楼\",\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-04-14T07:25:00\",\"PassengerName\":\"测试哆啦二梦\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"需英文客服;需英文司机;行李多需上门;需要女司机;\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":\"上海浦东国际机场T1\",\"CarTypeID\":0,\"OrderSource\":0}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-13T14:20:18.893","OrderUpdateTime":"2018-04-13T14:20:18.893","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"4766729960146224268","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":12140.0,\"Minutes\":0,\"Mileages\":1274,\"TimeOver\":0,\"MileagesOver\":1214,\"FromLongitude\":\"116.599563\",\"FromLatitude\":\"40.085493\",\"ToLongitude\":\"121.810627\",\"ToLatitude\":\"31.155566\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":1,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"657","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":"MU5101","CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":null,"Destination":"故宫博物院","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-04-14 10:15:00","PassengerName":"测试哆啦A梦","PassengerMobile":"15532650303","Remark":"需英文客服;需英文司机;行李多需上门;需要女司机;","Driver":null,"PaymentType":"","Address":"北京首都国际机场T2","CarTypeID":0,"OrderSource":0},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-54.51981445311111,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9327,"UserID":7708,"OrderCode":"1523600312520","ProductID":1,"CreateTime":"2018-04-13T14:18:32.52","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":1,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"657\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":\"MU5101\",\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":\"故宫博物院\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-04-14 10:15:00\",\"PassengerName\":\"测试哆啦A梦\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"需英文客服;需英文司机;行李多需上门;需要女司机;\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":\"北京首都国际机场T2\",\"CarTypeID\":0,\"OrderSource\":0}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-13T14:18:32.52","OrderUpdateTime":"2018-04-13T14:18:32.52","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5497579494735096016","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":28,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.599563\",\"FromLatitude\":\"40.085493\",\"ToLongitude\":\"116.403414\",\"ToLatitude\":\"39.924091\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"北京饭店","Destination":"北京首都国际机场-t2航站楼","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-13 15:2","PassengerName":"大三大四","PassengerMobile":"15532650303","Remark":"需英文客服;有老人或小朋友;需英文司机;行李多需上门;火车站接送服务;需要女司机;","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":0},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-73.73648111977778,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9313,"UserID":7708,"OrderCode":"1523588554941","ProductID":5,"CreateTime":"2018-04-13T11:02:34.94","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":5,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"740\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":null,\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"北京饭店\",\"Destination\":\"北京首都国际机场-t2航站楼\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-13 15:2\",\"PassengerName\":\"大三大四\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"需英文客服;有老人或小朋友;需英文司机;行李多需上门;火车站接送服务;需要女司机;\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-13T11:02:34.94","OrderUpdateTime":"2018-04-13T11:02:34.94","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5569029082134344440","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":28,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.41577\",\"FromLatitude\":\"39.91496\",\"ToLongitude\":\"116.599563\",\"ToLatitude\":\"40.085493\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"北京医院","Destination":"北京饭店","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-13 14:26","PassengerName":"等哈就开始的","PassengerMobile":"15532650303","Remark":"儿童安全座椅(6个月-4周岁);需英文客服;有老人或小朋友;需英文司机;儿童安全座椅(4-12周岁);行李多需上门;火车站接送服务;需要女司机;","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":0},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-74.33648546005556,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9311,"UserID":7708,"OrderCode":"1523586402504","ProductID":5,"CreateTime":"2018-04-13T10:26:42.503","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":5,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"740\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":null,\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"北京医院\",\"Destination\":\"北京饭店\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-13 14:26\",\"PassengerName\":\"等哈就开始的\",\"PassengerMobile\":\"15532650303\",\"Remark\":\"儿童安全座椅(6个月-4周岁);需英文客服;有老人或小朋友;需英文司机;儿童安全座椅(4-12周岁);行李多需上门;火车站接送服务;需要女司机;\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-13T10:26:42.503","OrderUpdateTime":"2018-04-13T10:26:42.503","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5660087646798524413","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":1,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.421484\",\"FromLatitude\":\"39.909989\",\"ToLongitude\":\"116.41577\",\"ToLatitude\":\"39.91496\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2},{"CarProduct":{"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"北京医院","Destination":"北京饭店","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-13 14:15","PassengerName":"创造性逢场作戏","PassengerMobile":"15532650304","Remark":"儿童安全座椅(6个月-4周岁);需英文客服;有老人或小朋友;需英文司机;儿童安全座椅(4-12周岁);行李多需上门;火车站接送服务;需要女司机;","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":0},"CardDesc":null,"HavePayMoney":480,"OrderStatusDesc":"待支付","ServiceBeginTimeSpan":-74.51981879338888,"validateDriverEvaluation":false,"listGrantAuthorize":null,"OrderId":9309,"UserID":7708,"OrderCode":"1523585722207","ProductID":5,"CreateTime":"2018-04-13T10:15:22.207","RegionId":0,"ShipAddress":null,"ShipZipCode":null,"ShipName":null,"ShipCellPhone":null,"PaymentTypeName":"微信支付","PaymentStatus":0,"Freight":0,"OrderTotal":480,"OrderStatus":0,"OrderProductInfo":"{\"OrderId\":0,\"DriverID\":null,\"ServiceType\":5,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":\"740\",\"ServiceCar\":\"E级\",\"CarBrand\":\"梅赛德斯-奔驰\",\"Price\":480.00,\"CarModelID\":\"39\",\"FlightNumber\":null,\"CarCity\":\"北京\",\"CarCityThree\":\"PEK\",\"ArrPortCode\":null,\"StartPlace\":\"北京医院\",\"Destination\":\"北京饭店\",\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":\"2018-4-13 14:15\",\"PassengerName\":\"创造性逢场作戏\",\"PassengerMobile\":\"15532650304\",\"Remark\":\"儿童安全座椅(6个月-4周岁);需英文客服;有老人或小朋友;需英文司机;儿童安全座椅(4-12周岁);行李多需上门;火车站接送服务;需要女司机;\",\"Driver\":null,\"PaymentType\":\"\",\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","Remark":null,"GrantAuthorizeMobile":null,"GrantAuthorizeUserID":0,"PaymentTime":"2018-04-13T10:15:22.207","OrderUpdateTime":"2018-04-13T10:15:22.207","OrderType":1,"ProductType":null,"Province":null,"City":null,"Area":null,"OrderSerialNumber":"5207471896164101672","ProductDescription":"E级","ApiOrderID":null,"DriftMoney":0,"DriftReason":null,"ReimburseMoney":0,"ReimburseReason":null,"OrderFlightInfo":"{\"TimeOverPrice\":0.0,\"MileagesOverPrice\":0.0,\"Minutes\":0,\"Mileages\":1,\"TimeOver\":0,\"MileagesOver\":0,\"FromLongitude\":\"116.421484\",\"FromLatitude\":\"39.909989\",\"ToLongitude\":\"116.41577\",\"ToLatitude\":\"39.91496\",\"ServicePrice\":200,\"ServiceOpen\":0,\"OrderId\":0,\"DriverID\":null,\"ServiceType\":0,\"DriverTel\":null,\"CardNo\":null,\"CardValue\":0.0,\"listGrantAuthorize\":null,\"listDriver\":null,\"IsVIP\":0,\"ProductID\":null,\"ServiceCar\":null,\"CarBrand\":null,\"Price\":0.0,\"CarModelID\":null,\"FlightNumber\":null,\"CarCity\":null,\"CarCityThree\":null,\"ArrPortCode\":null,\"StartPlace\":null,\"Destination\":null,\"ReturnTimeBoard\":null,\"ReturnStartPlace\":null,\"ReturnDestination\":null,\"TimeBoard\":null,\"PassengerName\":null,\"PassengerMobile\":null,\"Remark\":null,\"Driver\":null,\"PaymentType\":null,\"Address\":null,\"CarTypeID\":0,\"OrderSource\":0}","PriceDetail":null,"OrderMessage":null,"OrderSource":2}]
     * _metadata : {"code":"200","message":"success"}
     */
    private List<DataBean> data;
    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MetadataBean {
        /**
         * code : 200
         * message : success
         */

        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class DataBean {
        /**
         * CarProduct : {"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"北京南站","Destination":"故宫博物院","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-16 19:12","PassengerName":"测试多拉尔么好","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":2}
         * CardDesc : null
         * HavePayMoney : 480.0
         * OrderStatusDesc : 待支付
         * ServiceBeginTimeSpan : 2.4301855468888887
         * validateDriverEvaluation : false
         * listGrantAuthorize : null
         * OrderId : 9423
         * UserID : 7708
         * OrderCode : 1523862785691
         * ProductID : 5
         * CreateTime : 2018-04-16T15:13:05.69
         * RegionId : 0
         * ShipAddress : null
         * ShipZipCode : null
         * ShipName : null
         * ShipCellPhone : null
         * PaymentTypeName : 微信支付
         * PaymentStatus : 0
         * Freight : 0.0
         * OrderTotal : 480.0
         * OrderStatus : 0
         * OrderProductInfo : {"OrderId":0,"DriverID":null,"ServiceType":5,"DriverTel":null,"CardNo":null,"CardValue":0.0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":"740","ServiceCar":"E级","CarBrand":"梅赛德斯-奔驰","Price":480.00,"CarModelID":"39","FlightNumber":null,"CarCity":"北京","CarCityThree":"PEK","ArrPortCode":null,"StartPlace":"北京南站","Destination":"故宫博物院","ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":"2018-4-16 19:12","PassengerName":"测试多拉尔么好","PassengerMobile":"15532650303","Remark":"","Driver":null,"PaymentType":"","Address":null,"CarTypeID":0,"OrderSource":2}
         * Remark : null
         * GrantAuthorizeMobile : null
         * GrantAuthorizeUserID : 0
         * PaymentTime : 2018-04-16T15:13:05.69
         * OrderUpdateTime : 2018-04-16T15:13:05.69
         * OrderType : 1
         * ProductType : null
         * Province : null
         * City : null
         * Area : null
         * OrderSerialNumber : 4705217965980312775
         * ProductDescription : E级
         * ApiOrderID : null
         * DriftMoney : 0.0
         * DriftReason : null
         * ReimburseMoney : 0.0
         * ReimburseReason : null
         * OrderFlightInfo : {"TimeOverPrice":0.0,"MileagesOverPrice":0.0,"Minutes":0,"Mileages":7,"TimeOver":0,"MileagesOver":0,"FromLongitude":"116.385488","FromLatitude":"39.87128","ToLongitude":"116.403414","ToLatitude":"39.924091","ServicePrice":200,"ServiceOpen":0,"OrderId":0,"DriverID":null,"ServiceType":0,"DriverTel":null,"CardNo":null,"CardValue":0.0,"listGrantAuthorize":null,"listDriver":null,"IsVIP":0,"ProductID":null,"ServiceCar":null,"CarBrand":null,"Price":0.0,"CarModelID":null,"FlightNumber":null,"CarCity":null,"CarCityThree":null,"ArrPortCode":null,"StartPlace":null,"Destination":null,"ReturnTimeBoard":null,"ReturnStartPlace":null,"ReturnDestination":null,"TimeBoard":null,"PassengerName":null,"PassengerMobile":null,"Remark":null,"Driver":null,"PaymentType":null,"Address":null,"CarTypeID":0,"OrderSource":0}
         * PriceDetail : null
         * OrderMessage : null
         * OrderSource : 2
         */

        private CarProductBean CarProduct;
        private Object CardDesc;
        private double HavePayMoney;
        private String OrderStatusDesc;
        private double ServiceBeginTimeSpan;
        private boolean validateDriverEvaluation;
        private Object listGrantAuthorize;
        private int OrderId;
        private int UserID;
        private String OrderCode;
        private int ProductID;
        private String CreateTime;
        private int RegionId;
        private Object ShipAddress;
        private Object ShipZipCode;
        private Object ShipName;
        private Object ShipCellPhone;
        private String PaymentTypeName;
        private int PaymentStatus;
        private double Freight;
        private double OrderTotal;
        private int OrderStatus;
        private String OrderProductInfo;
        private Object Remark;
        private Object GrantAuthorizeMobile;
        private int GrantAuthorizeUserID;
        private String PaymentTime;
        private String OrderUpdateTime;
        private int OrderType;
        private Object ProductType;
        private Object Province;
        private Object City;
        private Object Area;
        private String OrderSerialNumber;
        private String ProductDescription;
        private Object ApiOrderID;
        private double DriftMoney;
        private Object DriftReason;
        private double ReimburseMoney;
        private Object ReimburseReason;
        private String OrderFlightInfo;
        private Object PriceDetail;
        private Object OrderMessage;
        private int OrderSource;

        public CarProductBean getCarProduct() {
            return CarProduct;
        }

        public void setCarProduct(CarProductBean CarProduct) {
            this.CarProduct = CarProduct;
        }

        public Object getCardDesc() {
            return CardDesc;
        }

        public void setCardDesc(Object CardDesc) {
            this.CardDesc = CardDesc;
        }

        public double getHavePayMoney() {
            return HavePayMoney;
        }

        public void setHavePayMoney(double HavePayMoney) {
            this.HavePayMoney = HavePayMoney;
        }

        public String getOrderStatusDesc() {
            return OrderStatusDesc;
        }

        public void setOrderStatusDesc(String OrderStatusDesc) {
            this.OrderStatusDesc = OrderStatusDesc;
        }

        public double getServiceBeginTimeSpan() {
            return ServiceBeginTimeSpan;
        }

        public void setServiceBeginTimeSpan(double ServiceBeginTimeSpan) {
            this.ServiceBeginTimeSpan = ServiceBeginTimeSpan;
        }

        public boolean isValidateDriverEvaluation() {
            return validateDriverEvaluation;
        }

        public void setValidateDriverEvaluation(boolean validateDriverEvaluation) {
            this.validateDriverEvaluation = validateDriverEvaluation;
        }

        public Object getListGrantAuthorize() {
            return listGrantAuthorize;
        }

        public void setListGrantAuthorize(Object listGrantAuthorize) {
            this.listGrantAuthorize = listGrantAuthorize;
        }

        public int getOrderId() {
            return OrderId;
        }

        public void setOrderId(int OrderId) {
            this.OrderId = OrderId;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getOrderCode() {
            return OrderCode;
        }

        public void setOrderCode(String OrderCode) {
            this.OrderCode = OrderCode;
        }

        public int getProductID() {
            return ProductID;
        }

        public void setProductID(int ProductID) {
            this.ProductID = ProductID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getRegionId() {
            return RegionId;
        }

        public void setRegionId(int RegionId) {
            this.RegionId = RegionId;
        }

        public Object getShipAddress() {
            return ShipAddress;
        }

        public void setShipAddress(Object ShipAddress) {
            this.ShipAddress = ShipAddress;
        }

        public Object getShipZipCode() {
            return ShipZipCode;
        }

        public void setShipZipCode(Object ShipZipCode) {
            this.ShipZipCode = ShipZipCode;
        }

        public Object getShipName() {
            return ShipName;
        }

        public void setShipName(Object ShipName) {
            this.ShipName = ShipName;
        }

        public Object getShipCellPhone() {
            return ShipCellPhone;
        }

        public void setShipCellPhone(Object ShipCellPhone) {
            this.ShipCellPhone = ShipCellPhone;
        }

        public String getPaymentTypeName() {
            return PaymentTypeName;
        }

        public void setPaymentTypeName(String PaymentTypeName) {
            this.PaymentTypeName = PaymentTypeName;
        }

        public int getPaymentStatus() {
            return PaymentStatus;
        }

        public void setPaymentStatus(int PaymentStatus) {
            this.PaymentStatus = PaymentStatus;
        }

        public double getFreight() {
            return Freight;
        }

        public void setFreight(double Freight) {
            this.Freight = Freight;
        }

        public double getOrderTotal() {
            return OrderTotal;
        }

        public void setOrderTotal(double OrderTotal) {
            this.OrderTotal = OrderTotal;
        }

        public int getOrderStatus() {
            return OrderStatus;
        }

        public void setOrderStatus(int OrderStatus) {
            this.OrderStatus = OrderStatus;
        }

        public String getOrderProductInfo() {
            return OrderProductInfo;
        }

        public void setOrderProductInfo(String OrderProductInfo) {
            this.OrderProductInfo = OrderProductInfo;
        }

        public Object getRemark() {
            return Remark;
        }

        public void setRemark(Object Remark) {
            this.Remark = Remark;
        }

        public Object getGrantAuthorizeMobile() {
            return GrantAuthorizeMobile;
        }

        public void setGrantAuthorizeMobile(Object GrantAuthorizeMobile) {
            this.GrantAuthorizeMobile = GrantAuthorizeMobile;
        }

        public int getGrantAuthorizeUserID() {
            return GrantAuthorizeUserID;
        }

        public void setGrantAuthorizeUserID(int GrantAuthorizeUserID) {
            this.GrantAuthorizeUserID = GrantAuthorizeUserID;
        }

        public String getPaymentTime() {
            return PaymentTime;
        }

        public void setPaymentTime(String PaymentTime) {
            this.PaymentTime = PaymentTime;
        }

        public String getOrderUpdateTime() {
            return OrderUpdateTime;
        }

        public void setOrderUpdateTime(String OrderUpdateTime) {
            this.OrderUpdateTime = OrderUpdateTime;
        }

        public int getOrderType() {
            return OrderType;
        }

        public void setOrderType(int OrderType) {
            this.OrderType = OrderType;
        }

        public Object getProductType() {
            return ProductType;
        }

        public void setProductType(Object ProductType) {
            this.ProductType = ProductType;
        }

        public Object getProvince() {
            return Province;
        }

        public void setProvince(Object Province) {
            this.Province = Province;
        }

        public Object getCity() {
            return City;
        }

        public void setCity(Object City) {
            this.City = City;
        }

        public Object getArea() {
            return Area;
        }

        public void setArea(Object Area) {
            this.Area = Area;
        }

        public String getOrderSerialNumber() {
            return OrderSerialNumber;
        }

        public void setOrderSerialNumber(String OrderSerialNumber) {
            this.OrderSerialNumber = OrderSerialNumber;
        }

        public String getProductDescription() {
            return ProductDescription;
        }

        public void setProductDescription(String ProductDescription) {
            this.ProductDescription = ProductDescription;
        }

        public Object getApiOrderID() {
            return ApiOrderID;
        }

        public void setApiOrderID(Object ApiOrderID) {
            this.ApiOrderID = ApiOrderID;
        }

        public double getDriftMoney() {
            return DriftMoney;
        }

        public void setDriftMoney(double DriftMoney) {
            this.DriftMoney = DriftMoney;
        }

        public Object getDriftReason() {
            return DriftReason;
        }

        public void setDriftReason(Object DriftReason) {
            this.DriftReason = DriftReason;
        }

        public double getReimburseMoney() {
            return ReimburseMoney;
        }

        public void setReimburseMoney(double ReimburseMoney) {
            this.ReimburseMoney = ReimburseMoney;
        }

        public Object getReimburseReason() {
            return ReimburseReason;
        }

        public void setReimburseReason(Object ReimburseReason) {
            this.ReimburseReason = ReimburseReason;
        }

        public String getOrderFlightInfo() {
            return OrderFlightInfo;
        }

        public void setOrderFlightInfo(String OrderFlightInfo) {
            this.OrderFlightInfo = OrderFlightInfo;
        }

        public Object getPriceDetail() {
            return PriceDetail;
        }

        public void setPriceDetail(Object PriceDetail) {
            this.PriceDetail = PriceDetail;
        }

        public Object getOrderMessage() {
            return OrderMessage;
        }

        public void setOrderMessage(Object OrderMessage) {
            this.OrderMessage = OrderMessage;
        }

        public int getOrderSource() {
            return OrderSource;
        }

        public void setOrderSource(int OrderSource) {
            this.OrderSource = OrderSource;
        }

        public static class CarProductBean {
            /**
             * OrderId : 0
             * DriverID : null
             * ServiceType : 5
             * DriverTel : null
             * CardNo : null
             * CardValue : 0.0
             * listGrantAuthorize : null
             * listDriver : null
             * IsVIP : 0
             * ProductID : 740
             * ServiceCar : E级
             * CarBrand : 梅赛德斯-奔驰
             * Price : 480.0
             * CarModelID : 39
             * FlightNumber : null
             * CarCity : 北京
             * CarCityThree : PEK
             * ArrPortCode : null
             * StartPlace : 北京南站
             * Destination : 故宫博物院
             * ReturnTimeBoard : null
             * ReturnStartPlace : null
             * ReturnDestination : null
             * TimeBoard : 2018-4-16 19:12
             * PassengerName : 测试多拉尔么好
             * PassengerMobile : 15532650303
             * Remark :
             * Driver : null
             * PaymentType :
             * Address : null
             * CarTypeID : 0
             * OrderSource : 2
             */

            private int OrderId;
            private Object DriverID;
            private int ServiceType;
            private Object DriverTel;
            private Object CardNo;
            private double CardValue;
            private Object listGrantAuthorize;
            private Object listDriver;
            private int IsVIP;
            private String ProductID;
            private String ServiceCar;
            private String CarBrand;
            private double Price;
            private String CarModelID;
            private Object FlightNumber;
            private String CarCity;
            private String CarCityThree;
            private Object ArrPortCode;
            private String StartPlace;
            private String Destination;
            private Object ReturnTimeBoard;
            private Object ReturnStartPlace;
            private Object ReturnDestination;
            private String TimeBoard;
            private String PassengerName;
            private String PassengerMobile;
            private String Remark;
            private Object Driver;
            private String PaymentType;
            private Object Address;
            private int CarTypeID;
            private int OrderSource;

            public int getOrderId() {
                return OrderId;
            }

            public void setOrderId(int OrderId) {
                this.OrderId = OrderId;
            }

            public Object getDriverID() {
                return DriverID;
            }

            public void setDriverID(Object DriverID) {
                this.DriverID = DriverID;
            }

            public int getServiceType() {
                return ServiceType;
            }

            public void setServiceType(int ServiceType) {
                this.ServiceType = ServiceType;
            }

            public Object getDriverTel() {
                return DriverTel;
            }

            public void setDriverTel(Object DriverTel) {
                this.DriverTel = DriverTel;
            }

            public Object getCardNo() {
                return CardNo;
            }

            public void setCardNo(Object CardNo) {
                this.CardNo = CardNo;
            }

            public double getCardValue() {
                return CardValue;
            }

            public void setCardValue(double CardValue) {
                this.CardValue = CardValue;
            }

            public Object getListGrantAuthorize() {
                return listGrantAuthorize;
            }

            public void setListGrantAuthorize(Object listGrantAuthorize) {
                this.listGrantAuthorize = listGrantAuthorize;
            }

            public Object getListDriver() {
                return listDriver;
            }

            public void setListDriver(Object listDriver) {
                this.listDriver = listDriver;
            }

            public int getIsVIP() {
                return IsVIP;
            }

            public void setIsVIP(int IsVIP) {
                this.IsVIP = IsVIP;
            }

            public String getProductID() {
                return ProductID;
            }

            public void setProductID(String ProductID) {
                this.ProductID = ProductID;
            }

            public String getServiceCar() {
                return ServiceCar;
            }

            public void setServiceCar(String ServiceCar) {
                this.ServiceCar = ServiceCar;
            }

            public String getCarBrand() {
                return CarBrand;
            }

            public void setCarBrand(String CarBrand) {
                this.CarBrand = CarBrand;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public String getCarModelID() {
                return CarModelID;
            }

            public void setCarModelID(String CarModelID) {
                this.CarModelID = CarModelID;
            }

            public Object getFlightNumber() {
                return FlightNumber;
            }

            public void setFlightNumber(Object FlightNumber) {
                this.FlightNumber = FlightNumber;
            }

            public String getCarCity() {
                return CarCity;
            }

            public void setCarCity(String CarCity) {
                this.CarCity = CarCity;
            }

            public String getCarCityThree() {
                return CarCityThree;
            }

            public void setCarCityThree(String CarCityThree) {
                this.CarCityThree = CarCityThree;
            }

            public Object getArrPortCode() {
                return ArrPortCode;
            }

            public void setArrPortCode(Object ArrPortCode) {
                this.ArrPortCode = ArrPortCode;
            }

            public String getStartPlace() {
                return StartPlace;
            }

            public void setStartPlace(String StartPlace) {
                this.StartPlace = StartPlace;
            }

            public String getDestination() {
                return Destination;
            }

            public void setDestination(String Destination) {
                this.Destination = Destination;
            }

            public Object getReturnTimeBoard() {
                return ReturnTimeBoard;
            }

            public void setReturnTimeBoard(Object ReturnTimeBoard) {
                this.ReturnTimeBoard = ReturnTimeBoard;
            }

            public Object getReturnStartPlace() {
                return ReturnStartPlace;
            }

            public void setReturnStartPlace(Object ReturnStartPlace) {
                this.ReturnStartPlace = ReturnStartPlace;
            }

            public Object getReturnDestination() {
                return ReturnDestination;
            }

            public void setReturnDestination(Object ReturnDestination) {
                this.ReturnDestination = ReturnDestination;
            }

            public String getTimeBoard() {
                return TimeBoard;
            }

            public void setTimeBoard(String TimeBoard) {
                this.TimeBoard = TimeBoard;
            }

            public String getPassengerName() {
                return PassengerName;
            }

            public void setPassengerName(String PassengerName) {
                this.PassengerName = PassengerName;
            }

            public String getPassengerMobile() {
                return PassengerMobile;
            }

            public void setPassengerMobile(String PassengerMobile) {
                this.PassengerMobile = PassengerMobile;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }

            public Object getDriver() {
                return Driver;
            }

            public void setDriver(Object Driver) {
                this.Driver = Driver;
            }

            public String getPaymentType() {
                return PaymentType;
            }

            public void setPaymentType(String PaymentType) {
                this.PaymentType = PaymentType;
            }

            public Object getAddress() {
                return Address;
            }

            public void setAddress(Object Address) {
                this.Address = Address;
            }

            public int getCarTypeID() {
                return CarTypeID;
            }

            public void setCarTypeID(int CarTypeID) {
                this.CarTypeID = CarTypeID;
            }

            public int getOrderSource() {
                return OrderSource;
            }

            public void setOrderSource(int OrderSource) {
                this.OrderSource = OrderSource;
            }
        }
    }
}
