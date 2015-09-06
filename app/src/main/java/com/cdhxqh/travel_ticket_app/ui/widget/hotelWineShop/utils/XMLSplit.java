package com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.utils;


import com.cdhxqh.travel_ticket_app.model.hotel.HotelModel;
import com.cdhxqh.travel_ticket_app.model.hotel.HotelOneModel;
import com.cdhxqh.travel_ticket_app.model.hotel.RoomContent;
import com.cdhxqh.travel_ticket_app.model.hotel.RoomFacility;
import com.cdhxqh.travel_ticket_app.model.hotel.RoomImgModel;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/3.
 */
public class XMLSplit {
    public static ArrayList<HotelModel> xmlSplit(String xml) {
        try {
            //创建一个新的字符串
            StringReader read = new StringReader(xml);
            //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            //创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            List jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            List o = ((Element) jiedian.get(1)).getChildren();
            List propertys = ((Element) o.get(0)).getChildren();
            List property = ((Element) propertys.get(0)).getChildren();
            Namespace ns = root.getNamespace();
            Element propertyElement = null;
            ArrayList<HotelModel> hotelModelArrayList = new ArrayList<HotelModel>();
            for (int i = 0; i < property.size(); i++) {
                HotelModel hotelModel = new HotelModel();
                propertyElement = (Element) property.get(i);//循环依次得到子元素

                String hotelCode = propertyElement.getAttributeValue("HotelCode");
                String hotelName = propertyElement.getAttributeValue("HotelName");
                hotelModel.setHotelCode(hotelCode);
                hotelModel.setHotelName(hotelName);

                /**
                 * 获取property下的属性
                 */
                List elements = ((Element) propertyElement).getChildren();
                for (int j = 0; j < elements.size(); j++) {
                    Element element = (Element) elements.get(j);
                    String name = element.getName();
                    if (name.equals("Position")) {
                        String Latitude = element.getAttributeValue("Latitude");
                        String Longitude = element.getAttributeValue("Longitude");
                        hotelModel.setLatitude(Latitude);
                        hotelModel.setLongitude(Longitude);
                    } else if (name.equals("Address")) {
                        List addressList = ((Element) element).getChildren();
                        for (int k = 0; k < addressList.size(); k++) {
                            if (((Element) addressList.get(k)).getName().equals("AddressLine")) {
                                String addressName = ((Element) addressList.get(k)).getText();
                                hotelModel.setAddress(addressName);
                            }
                        }
                    } else if (name.equals("Award")) {
                        if (element.getAttributeValue("Provider").equals("HotelStarRate")) {
                            String rate = element.getAttributeValue("Rating");
                            hotelModel.setHotelStarRate(rate);
                        }
                    } else if (name.equals("RelativePosition")) {

                    }
                }

                hotelModelArrayList.add(hotelModel);
            }
            return hotelModelArrayList;

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 酒店介绍
     */
    public static HotelOneModel xmlContentSplit(String xml) {
        try {
            HotelOneModel hotelOneModel = new HotelOneModel();
            //创建一个新的字符串
            StringReader read = new StringReader(xml);
            //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            //创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            List jiedian = root.getChildren();
            List hotelResponse = ((Element) jiedian.get(1)).getChildren();
            List OTA = ((Element) hotelResponse.get(0)).getChildren();
            List HotelDescriptiveContents = ((Element) OTA.get(0)).getChildren();
            List HotelDescriptiveContent = ((Element) HotelDescriptiveContents.get(0)).getChildren();


            /**
             * 酒店规则
             */
            List Policies = ((Element) HotelDescriptiveContent.get(2)).getChildren();
            List Policy = ((Element) Policies.get(0)).getChildren();
            List PolicyInfoCodes = ((Element) Policy.get(0)).getChildren();
            List PolicyInfoCode = ((Element) PolicyInfoCodes.get(0)).getChildren();
            for(int i = 0; i<PolicyInfoCode.size(); i++) {
                Element Description = ((Element)PolicyInfoCode.get(i));
                String name = Description.getAttributeValue("Name");
                if(name.equals("ArrivalAndDeparture")) {
                    List text = Description.getChildren();
                    hotelOneModel.setArrivalAndDeparture(((Element)text.get(0)).getText());
                }else if(name.equals("Cancel")) {
                    List text = Description.getChildren();
                    hotelOneModel.setCancel(((Element) text.get(0)).getText());
                }else if(name.equals("DepositAndPrepaid")) {
                    List text = Description.getChildren();
                    hotelOneModel.setDepositAndPrepaid(((Element) text.get(0)).getText());
                }else if(name.equals("Pet")) {
                    List text = Description.getChildren();
                    hotelOneModel.setPet(((Element) text.get(0)).getText());
                }else if(name.equals("Requirements")) {
                    List text = Description.getChildren();
                    hotelOneModel.setRequirements(((Element) text.get(0)).getText());
                }
            }
            hotelOneModel.setCheckInTime(((Element)Policy.get(1)).getAttributeValue("CheckInTime"));



            /**
             * 房间列表
             */
            List FacilityInfo = ((Element) HotelDescriptiveContent.get(1)).getChildren();
            List GuestRooms = ((Element) FacilityInfo.get(0)).getChildren();
//            List FacilityInfo = ((Element) HotelDescriptiveContent.get(0)).getChildren();
//            List GuestRooms = ((Element) FacilityInfo.get(0)).getChildren();

            ArrayList<RoomContent> roomContentList = new ArrayList<RoomContent>();
            for (int i = 0; i < GuestRooms.size(); i++) {
                RoomContent roomContent = new RoomContent();
                /**
                 * 房间类型
                 */
                String GuestRoomName = ((Element) GuestRooms.get(i)).getAttributeValue("RoomTypeName");
                roomContent.setRoomTypeName(GuestRoomName);//房间类型名
                ArrayList<RoomFacility> roomFacility = new ArrayList<RoomFacility>();
                List type = ((Element) GuestRooms.get(i)).getChildren();
                for (int j = 0; j < type.size(); j++) {
                    Element a = ((Element) type.get(j));
                    String name = a.getName();
                    if (name.equals("TPA_Extensions")) {
                        List TPA_Extension = a.getChildren();
                        for (int k = 0; k < TPA_Extension.size(); k++) {
                            List TPA_Extension_a = ((Element) TPA_Extension.get(k)).getChildren();
                            for (int h = 0; h < TPA_Extension_a.size(); h++) {
                                RoomFacility roomFacility1 = new RoomFacility();
                                Element element = ((Element) TPA_Extension_a.get(h));
                                String names = element.getName();
                                if (names.equals("FacilityName")) {
                                    roomFacility1.setFacilityName(element.getText());
                                } else if (names.equals("FTypeName")) {
                                    roomFacility1.setFTypeName(element.getText());
                                } else if (names.equals("FacilityValue")) {
                                    roomFacility1.setFacilityValue(element.getText());
                                }
                                roomFacility.add(roomFacility1);
                            }
                        }
                        roomContent.setRoomFacilities(roomFacility);
                    } else if (name.equals("TypeRoom")) {
                        roomContent.setBedTypeCode(a.getAttributeValue("BedTypeCode"));
                        roomContent.setFloor(a.getAttributeValue("Floor"));
                        roomContent.setHasWindow(a.getAttributeValue("HasWindow"));
                        roomContent.setInvBlockCode(a.getAttributeValue("InvBlockCode"));
                        roomContent.setName(a.getAttributeValue("Name"));
                        roomContent.setNonSmoking(a.getAttributeValue("NonSmoking"));
                        roomContent.setQuantity(a.getAttributeValue("Quantity"));
                        roomContent.setRoomSize(a.getAttributeValue("RoomSize"));
                        roomContent.setRoomTypeCode(a.getAttributeValue("RoomTypeCode"));
                        roomContent.setSize(a.getAttributeValue("Size"));
                        roomContent.setStandardOccupancy(a.getAttributeValue("StandardOccupancy"));
                    }
                }
                roomContentList.add(roomContent);
            }
            hotelOneModel.setRoomContentList(roomContentList);

            /**
             * 酒店图片
             */
            List MultimediaDescriptions = ((Element) HotelDescriptiveContent.get(5)).getChildren();
            List MultimediaDescription = ((Element) MultimediaDescriptions.get(0)).getChildren();
            List ImageItems = ((Element)MultimediaDescription.get(0)).getChildren();
            ArrayList<RoomImgModel> roomImgModelList = new ArrayList<RoomImgModel>();
            for(int i = 0; i<ImageItems.size(); i++) {
                List ImageItem = ((Element)ImageItems.get(i)).getChildren();
                RoomImgModel roomImgModel = new RoomImgModel();
                for(int j = 0; j<ImageItem.size(); j++) {
                    Element e = ((Element) ImageItem.get(j));
                    String name = e.getName();
                    if(name.equals("ImageFormat")) {
                        String url = ((Element)e.getChildren().get(0)).getText();
                        roomImgModel.setUrl(url);
                    }else if(name.equals("Description")) {
                        String Caption = e.getAttributeValue("Caption");
                        roomImgModel.setCaption(Caption);
                    }else if(name.equals("TPA_Extensions")) {
                        String InvBlockCode = ((Element)e.getChildren().get(0)).getText();
                        roomImgModel.setInvBlockCode(InvBlockCode);
                    }
                }
                roomImgModelList.add(roomImgModel);
            }
            hotelOneModel.setRoomImgModelList(roomImgModelList);

//            List MultimediaDescription1 = ((Element) MultimediaDescriptions.get(1)).getChildren();

            return hotelOneModel;
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
