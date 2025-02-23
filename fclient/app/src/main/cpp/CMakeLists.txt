# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name.
project("fclient")

# Create the shared library for your project
add_library(${CMAKE_PROJECT_NAME} SHARED
        native-lib.cpp)

# Specifies the libraries to link to the target library
target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        spdlog
        mbedcrypto
        ${log-lib}
        log)

# Define spdlog as an imported library
add_library(spdlog STATIC IMPORTED)

# Set the correct path to the compiled spdlog library
set_target_properties(spdlog PROPERTIES IMPORTED_LOCATION
        C:/Users/vlax6/AndroidStudioProjects/rpo/libs/spdlog/build/${ANDROID_ABI}/libspdlog.a)

# Set the path to spdlog headers
target_include_directories(${CMAKE_PROJECT_NAME} PUBLIC
        C:/Users/vlax6/AndroidStudioProjects/rpo/libs/spdlog/spdlog/include)

# Define mbedcrypto as an imported shared library
add_library(mbedcrypto SHARED IMPORTED)

# Set the correct path for mbedcrypto library
set_target_properties(mbedcrypto PROPERTIES IMPORTED_LOCATION
        ${CMAKE_CURRENT_SOURCE_DIR}/../nativeLibs/${ANDROID_ABI}/libmbedcrypto.so)

# Set the path to mbedcrypto headers (if you need them)
target_include_directories(${CMAKE_PROJECT_NAME} PUBLIC
        C:/Users/vlax6/AndroidStudioProjects/rpo/libs/spdlog/spdlog/include
        C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/mbedtls/include)
