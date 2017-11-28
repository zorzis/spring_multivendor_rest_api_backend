<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:mc="http://www.w3.org/1999/xhtml">
<head>

    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0;" />
    <title>Dimo</title>

    <style type="text/css">

        body{width: 100%; background-color: #EEEEEE; margin:0; padding:0; -webkit-font-smoothing: antialiased;mso-margin-top-alt:0px; mso-margin-bottom-alt:0px; mso-padding-alt: 0px 0px 0px 0px;}

        p,h1,h2,h3,h4{margin-top:0;margin-bottom:0;padding-top:0;padding-bottom:0;}

        span.preheader{display: none; font-size: 1px;}

        html{width: 100%;}

        table{font-size: 12px;border: 0;}

        .menu-space{padding-right:25px;}


        @media only screen and (max-width:640px)

        {
            body{width:auto!important;}
            body[yahoo] .main {width:440px !important;}
            body[yahoo] .two-left{width:420px !important; margin:0px auto;}
            body[yahoo] .full{width:100% !important; margin:0px auto;}
            body[yahoo] .alaine{ text-align:center;}
            body[yahoo] .menu-space{padding-right:0px;}

        }

        @media only screen and (max-width:479px)
        {
            body{width:auto!important;}
            body[yahoo] .main {width:280px !important;}
            body[yahoo] .two-left{width:270px !important; margin:0px auto;}
            body[yahoo] .full{width:100% !important; margin:0px auto;}
            body[yahoo] .alaine{ text-align:center;}
            body[yahoo] .menu-space{padding-right:0px;}


        }


    </style>

</head>

<body yahoo="fix" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!--Main Table start-->

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#2b2a2a" style="background:#2b2a2a;">
    <tr>
        <td align="center" valign="top">


            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td height="62" align="left" valign="top" style="line-height:62px;">&nbsp;</td>
                        </tr>
                    </table></td>
                </tr>
            </table>


            <!--Top image start-->

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td align="left" valign="top"><img src="images/border.png" style="display:block;" width="500" height="34" alt="" /></td>
                        </tr>
                    </table></td>
                </tr>
            </table>

            <!--Top image End-->


            <!--Invoice date start-->

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td align="left" valign="top" bgcolor="#000000" style="background:#FFF;"><table width="445" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td height="18" align="left" valign="top">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top">


                                        <table width="180" border="0" align="left" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td align="left" valign="top"><img src="images/space.png" width="4" height="4" alt="" /></td>
                                            </tr>
                                            <tr>
                                                <td align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#454343;" mc:edit="invoice-shoppingCartID">
                                                    <p>Order ID</p>
                                                    <multiline><b style="color:#e76046;">#${orderDTO.getOrderID()}</b></multiline>
                                                </td>
                                            </tr>
                                        </table>


                                        <table width="180" border="0" align="right" cellpadding="0" cellspacing="0">

                                            <tr>
                                                <td align="right" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#4a4a4a; font-weight:normal; line-height:18px; padding-right:2px;" mc:edit="invoice-date"><multiline>${orderDTO.getDateOrderPlaced()}</multiline></td>
                                            </tr>
                                        </table>



                                    </td>
                                </tr>

                            </table></td>
                        </tr>
                    </table></td>
                </tr>
            </table>

            <!--Invoice date End-->


            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td align="left" valign="top" bgcolor="#000000" style="background:#FFF;"><table width="445" border="0" align="center" cellpadding="0" cellspacing="0">

                                <tr>
                                    <td height="20" align="left" valign="top">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="center" valign="top" style="border-top:#edebeb solid 1px;">&nbsp;</td>
                                </tr>
                            </table></td>
                        </tr>
                    </table></td>
                </tr>
            </table>

            <!--Invoice Billing start-->

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td align="left" valign="top" bgcolor="#FFFFFF" style="background:#FFF;"><table width="445" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td align="left" valign="top">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top">


                                        <table width="200" border="0" align="left" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td align="left" valign="top"><img mc:edit="logo" editable="true" src="/WEB-INF/email_templates/images/haze_logo_white_background.png" width="112" height="41" alt="" /></td>
                                            </tr>
                                            <tr>
                                                <td align="left" valign="top">&nbsp;</td>
                                            </tr>
                                        </table>


                                        <table width="160" border="0" align="right" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:18px; font-weight:bold; color:#454343;" mc:edit="invoice-bill"><multiline>Billing</multiline></td>
                                            </tr>
                                            <tr>
                                                <td align="left" valign="top"><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td height="6" align="left" valign="top"><img src="images/space.png" width="4" height="4" alt="" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#4a4a4a; font-weight:normal; line-height:20px; padding-right:2px;" mc:edit="invoice-bill-inner">
                                                            <multiline>
                                                                Mr/Mrs ${orderDTO.clientDetails.orderClientFirstName} ${orderDTO.clientDetails.orderClientLastName},
                                                                <br />
                                                                ${orderDTO.clientAddressDetails.street} ${orderDTO.clientAddressDetails.streetNumber} ${orderDTO.clientAddressDetails.city}
                                                                    <br />
                                                            ${orderDTO.clientAddressDetails.state} ${orderDTO.clientAddressDetails.country}
                                                                <br />
                                                            Postal Code: ${orderDTO.clientAddressDetails.postalCode}
                                                                <br />
                                                                Floor: ${orderDTO.clientAddressDetails.floor}
                                                            </multiline>



                                                        </td>
                                                    </tr>
                                                </table></td>
                                            </tr>
                                        </table>


                                    </td>
                                </tr>
                                <tr>
                                    <td height="20" align="left" valign="top">&nbsp;</td>
                                </tr>
                            </table></td>
                        </tr>
                    </table></td>
                </tr>
            </table>

            <!--Invoice Billing End-->


            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td align="left" valign="top" bgcolor="#000000" style="background:#FFF;"><table width="445" border="0" align="center" cellpadding="0" cellspacing="0">

                                <tr>
                                    <td height="20" align="left" valign="top">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="center" valign="top" style="border-top:#edebeb solid 1px;">&nbsp;</td>
                                </tr>
                            </table></td>
                        </tr>
                    </table></td>
                </tr>
            </table>


            <!--Invoice summary start-->

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td align="left" valign="top" bgcolor="#FFFFFF" style="background:#FFF;"><table width="445" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td align="left" valign="top">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:24px; font-weight:normal; color:#e76046; text-transform:uppercase;" mc:edit="invoice-summary"><multiline>Invoice Summary</multiline></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top">&nbsp;</td>
                                </tr>
                            </table></td>
                        </tr>
                    </table></td>
                </tr>
            </table>

            <!--Invoice summary End-->


            <!--Invoice title start-->

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td align="left" valign="top" bgcolor="#FFFFFF" style="background:#FFF;"><table width="445" border="0" align="center" cellpadding="0" cellspacing="0">

                                <tr>
                                    <td align="left" valign="top"><table width="445" border="0" align="left" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td width="250" align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold; color:#454343;" mc:edit="invoice-des"><multiline>Description</multiline></td>
                                            <td width="66" align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold; color:#454343;" mc:edit="invoice-price"><multiline>Price</multiline></td>
                                            <td width="57" align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold; color:#454343;" mc:edit="invoice-qty"><multiline>Liters</multiline></td>
                                            <td width="72" align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold; color:#454343;" mc:edit="invoice-total"><multiline>Line total</multiline></td>
                                        </tr>
                                        <tr>
                                            <td height="12" colspan="4" align="left" valign="top" style="border-bottom:#e2e2e2 solid 1px;"><img src="images/space.png" width="4" height="4" alt="" /></td>
                                        </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top">&nbsp;</td>
                                </tr>
                            </table></td>
                        </tr>
                    </table></td>
                </tr>
            </table>

            <!--Invoice title End-->


            <!--Invoice Items start-->
            <#list orderDTO.getOrderProducts() as product>
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                            <tr>
                                <td align="left" valign="top" bgcolor="#FFFFFF" style="background:#FFF;"><table width="445" border="0" align="center" cellpadding="0" cellspacing="0">

                                    <tr>
                                        <td align="left" valign="top">
                                            <table width="445" border="0" align="left" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td width="250" align="left" valign="top">
                                                    <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:13px; font-weight:bold; color:#454343; line-height:20px;" mc:edit="invoice-item1-title"><multiline>${product.orderProductCategoryName}</multiline></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="left" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#4a4a4a; font-weight:normal; line-height:18px;" mc:edit="invoice-item1-des"><multiline>${product.orderProductDescription}</multiline></td>
                                                    </tr>
                                                </table>
                                                </td>
                                                <td width="66" align="left" valign="middle" style="font-family:Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#e76046;" mc:edit="invoice-item1-price"><multiline>${product.orderProductPrice}&euro;</multiline></td>
                                                <td width="57" align="left" valign="middle" style="font-family:Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#e76046;" mc:edit="invoice-item1-qty"><multiline>${product.orderProductQuantity}</multiline></td>
                                                <td width="72" align="left" valign="middle" style="font-family:Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#e76046;" mc:edit="invoice-item1-price2">
                                                    <multiline>
                                                        <#function productTotal productPrice productQuantity>
                                                            <#return (productPrice *  productQuantity)>
                                                        </#function>
                                                        ${productTotal(product.orderProductPrice,product.orderProductQuantity)}
                                                    </multiline>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td height="12" colspan="4" align="left" valign="top" style="border-bottom:#e2e2e2 solid 1px;"><img src="images/space.png" width="4" height="4" alt="" /></td>
                                            </tr>
                                        </table></td>
                                    </tr>
                                    <tr>
                                        <td align="left" valign="top">&nbsp;</td>
                                    </tr>
                                </table></td>
                            </tr>
                        </table></td>
                    </tr>
                </table>
            </#list>




            <!--Invoice total start-->

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td align="left" valign="top" bgcolor="#FFFFFF" style="background:#FFF;"><table width="445" border="0" align="center" cellpadding="0" cellspacing="0">

                                <tr>
                                    <td align="left" valign="top"><table width="205" border="0" align="right" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td align="left" valign="top" style="border:#e2e2e2 solid 1px; border-top:none;"><table width="203" border="0" align="center" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td width="75" height="50" align="center" valign="middle" style="border-right:#e2e2e2 solid 1px; font-family:Arial, Helvetica, sans-serif; font-size:13px; font-weight:bold; color:#454343; line-height:20px;" mc:edit="invoice-tax-total"><multiline>SubTotal</multiline></td>
                                                    <td width="128" align="right" valign="middle" style="font-family:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold; color:#454343; line-height:20px; padding-right:17px;" mc:edit="invoice-tax-total-inner"><multiline>${orderDTO.getSubTotalOrderPrice()} &euro;</multiline></td>
                                                </tr>
                                                <tr>
                                                    <td width="75" height="50" align="center" valign="middle" style="border:#e2e2e2 solid 1px; border-left:none; font-family:Arial, Helvetica, sans-serif; font-size:13px; font-weight:bold; color:#454343; line-height:20px;" mc:edit="invoice-tax"><multiline>Tax</multiline></td>
                                                    <td width="128" align="right" valign="middle" style="border:#e2e2e2 solid 1px; border-left:none; border-right:none; font-family:Arial, Helvetica, sans-serif; font-size:16px; font-weight:bold; color:#454343; line-height:20px; padding-right:17px;" mc:edit="invoice-tax-inner"><multiline>${orderDTO.getTotalProductsTax()} &euro;</multiline></td>
                                                </tr>
                                                <tr>
                                                    <td height="50" colspan="2" align="right" valign="middle" style="font-family:Arial, Helvetica, sans-serif; font-size:30px; font-weight:bold; color:#454343; line-height:20px; padding-right:17px;" mc:edit="invoice-gr-total"><multiline>${orderDTO.getTotalOrderPrice()} &euro;</multiline></td>
                                                </tr>
                                            </table></td>
                                        </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                    <td height="50" align="left" valign="top">&nbsp;</td>
                                </tr>
                            </table></td>
                        </tr>
                    </table></td>
                </tr>
            </table>

            <!--Invoice total End-->


            <!--Copyright start-->

            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td align="left" valign="top" bgcolor="#e76046" style="background:#e76046; -moz-border-radius: 0px 0px 15px 15px; border-radius: 0px 0px 15px 15px;"><table width="445" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td height="20" align="left" valign="top">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td align="center" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#FFF; font-weight:normal;" mc:edit="invoice-copy-right"><multiline>Copyright 2017 &copy; haze.gr. All rights reserved</multiline></td>
                                        </tr>
                                        <tr>
                                            <td align="center" valign="top" style="font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#FFF; font-weight:normal; padding-top:4px;" mc:edit="invoice-subscribe"><unsubscribe> For any questions drop us an email: info@haze.gr </unsubscribe></td>
                                        </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top">&nbsp;</td>
                                </tr>
                            </table></td>
                        </tr>
                    </table></td>
                </tr>
            </table>

            <!--Copyright End-->


            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" valign="top"><table width="500" border="0" align="center" cellpadding="0" cellspacing="0" >
                        <tr>
                            <td height="62" align="left" valign="top" style="line-height:62px;">&nbsp;</td>
                        </tr>
                    </table></td>
                </tr>
            </table>


        </td>
    </tr>
</table>

<!--Main Table End-->

</body>
</html>
