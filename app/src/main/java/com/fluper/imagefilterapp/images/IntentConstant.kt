package com.fluper.imagefilterapp.images

object IntentConstant {



        const val PREFRENCE_NAME = "myPrefs"

        // Formats
        const val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        const val DEVICE_TYPE = "1"

        //Base Activity
        const val BACK_PRESS_TIME_INTERVAL: Long = 2000

        const val NUMBER_PATTERN = "[0-9]+"


        const val IS_FROM = "isFrom"

        //SignUp
        const val SIGN_UP = "user/registerUser"

        //Login
        const val LOGIN = "user/loginUser"

        //Forgot Password
        const val FORGOT_PASSWORD = "forgotPassword"

        //OTP
        const val OTP_VERIFY = "user/verifyOtp"
        const val OTP_RESEND = "user/sendResendOtp"
        const val FORGATE_PASSWORD = "user/forgotPassword"
        const val SAVE_ADRESS = "user/saveAddress"

        const val ADD_FAMILY_MEMBER = "user/addFamilyMember"
        const val RESET_PASSWORD = "user/resetPassIfForget"
        const val PROFILE_PICTURE = "user/uploadProfile"
        const val SOCIAL_LOGIN = "user/loginWithSocialAccount"
        const val GET_CATEGORY = "user/getCategoryList"
        const val GET_SEARCH_HISTORY = "user/getSearchHistory"
        const val GET_SEARCH_SUGGETION = "user/getSearchSuggetions"
        const val GET_FILTER_SEARCH = "user/getFilterSearch"
        const val SEARCH_CATEGORY = "user/searchThroughCategory"
        const val SEARCH_THROUGH_CATEGORY = "user/searchThroughCategory"
        const val TAILOR_DETAILS = "user/getTailorDetails"
        const val GET_FABRIC_TYPE = "user/getFabricTypes"

        //---------------M5------------------------------
        const val READY_MADE_PRODUCT_LIST = "user/readyMadeProductsList"
        const val TAILOR_CATEGORIES = "user/tailorCategories"
        const val GET_PRODUCTS = "user/getProducts"
        const val GET_PRODUCTS_DETAILS = "user/getProductDetails"
        const val GET_SIMILAR_PRODUCTS = "user/similarProducts"
        const val ADD_TO_CART = "user/addToCart"
        const val SHOW_CART = "user/showCart"
        const val CHANGE_QUANTITY = "user/changeQuantity"
        const val REMOVE_FROM_CART = "user/removeFromCart"
        const val SAVE_FOR_LATER = "user/saveForLater"
        const val MOVE_TO_CART = "user/moveToCart"
        const val GET_SAVE_FOR_LATER_LIST = "user/listOfSaveForLater"
        const val GET_SET_ADDRESS = "user/address"
        const val BUY_NOW_PRODUCT = "user/buyNow"
        const val PROCEED_TO_BUY = "user/processToBuy"
        const val PROCEED_TO_CONTINUE_CHECKOUT = "user/continueCheckout"
        const val PRODUCTS_IN_CART = "user/productsOnCart"
        const val PLACE_ORDER = "user/placeOrder"
        const val ADD_TO_WISHLIST = "user/addRemoveToWishList"
        const val ADD_REMOVE_TAILOR_TO_WISH_LIST = "user/addRemoveTailorToWishList"

        //----------------M6-----------------------------------
        const val FABRIC_COLOR = "user/getFabricColors"
        const val TAILOR_BRANDS = "user/getTailorBrands"
        const val FAMILY_MEMBERS = "user/getFamilyMember"
        const val FABRIC_LIST = "user/getFabricList"
        const val FABRIC_DETAILS = "user/getFabricDetails"
        const val TIME_SLOTS = "user/getTailorTimeSlot"
        const val OLD_MEASUREMENTS = "user/getMeasurment"

        //---------------M7--------------------------------------
        const val GET_PROMOTIONS = "user/getPromotions"
        const val GET_OFFERS = "user/getOffers"
        const val GET_PROD_OFFER = "user/getProductOffers"
        const val APPLY_OFFER = "user/applyOffer"

        //---------------M8-------------------------
        const val ORDER_LISTING = "user/orderlisting"
        const val ORDER_DETAILS = "user/orderDetails"
        const val CANCEL_ORDER = "user/cancelorder"
        const val REORDER = "user/reorder"
        const val GET_CANCEL_REASON = "user/fetchCancelReason"
        const val ADD_CANCEL_REASON = "user/addCancelReason"
        const val RATE_ORDER = "user/productrating"
        const val IMAGE_UPLOAD = "user/getImageLink"
        const val RESCHEDULE = "user/reSchedule"
        const val FAVORITE_LIST = "user/favoriteList"
        const val REMOVE_FAVORITE = "user/removeFavorite"
        const val GET_REWARD_LIST = "user/getRewardList"
        const val REWARD_POINT = "user/reward-point"
        const val TOTAL_REWARD_POINTS = "user/totalReward-points"
        const val INVITE_REWARDS_POINTS = "user/invite-rewards-points"
        // m10
        const val RAISE_ISSUE = "user/raise-issue"
        const val ISSUE_LIST = "user/issueList"
        const val ISSUE_DETAILS = "user/issueDetails"
        // m12
        const val VIEW_PROFILE = "user/viewProfile"
        const val UPDATE_PROFILE = "user/updateProfile"
        const val UPDATE_PASSWORD = "user/updatePassword"
        const val ADDRESS = "user/address"
        const val SAVE_ADDRESS = "user/saveAddress"
        const val GET_FAMILY_MEMBER = "user/getFamilyMember"
        const val DELETE_FAMILY_MEMBER = "user/deleteFamilyMember"


        const val Support_User = 2
        const val Support_Admin = 1

        const val DEAL_FOR_YOU = "dealForYou"
        const val DEAL_NEAR_YOU = "dealNearYou"


        const val GALLERY = 1111
        const val CAMERA = 2222
        const val VIDEO = 3333


        // Media Type
        const val MEDIA_TYPE_IMAGE = 1
        const val MEDIA_TYPE_VIDEO = 2
        val IMAGE_MAX_SIZE = 1024
}