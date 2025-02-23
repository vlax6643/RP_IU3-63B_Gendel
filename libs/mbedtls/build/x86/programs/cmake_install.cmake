# Install script for directory: C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/mbedtls/programs

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "C:/Program Files (x86)/Mbed TLS")
endif()
string(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
if(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  if(BUILD_TYPE)
    string(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  else()
    set(CMAKE_INSTALL_CONFIG_NAME "")
  endif()
  message(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
endif()

# Set the component getting installed.
if(NOT CMAKE_INSTALL_COMPONENT)
  if(COMPONENT)
    message(STATUS "Install component: \"${COMPONENT}\"")
    set(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  else()
    set(CMAKE_INSTALL_COMPONENT)
  endif()
endif()

# Install shared libraries without execute permission?
if(NOT DEFINED CMAKE_INSTALL_SO_NO_EXE)
  set(CMAKE_INSTALL_SO_NO_EXE "0")
endif()

# Is this installation the result of a crosscompile?
if(NOT DEFINED CMAKE_CROSSCOMPILING)
  set(CMAKE_CROSSCOMPILING "TRUE")
endif()

# Set path to fallback-tool for dependency-resolution.
if(NOT DEFINED CMAKE_OBJDUMP)
  set(CMAKE_OBJDUMP "C:/Users/vlax6/AppData/Local/Android/Sdk/ndk/28.0.13004108/toolchains/llvm/prebuilt/windows-x86_64/bin/llvm-objdump.exe")
endif()

if(NOT CMAKE_INSTALL_LOCAL_ONLY)
  # Include the install script for each subdirectory.
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/aes/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/cipher/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/fuzz/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/hash/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/pkey/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/psa/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/random/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/ssl/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/test/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/util/cmake_install.cmake")
  include("C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/x509/cmake_install.cmake")

endif()

string(REPLACE ";" "\n" CMAKE_INSTALL_MANIFEST_CONTENT
       "${CMAKE_INSTALL_MANIFEST_FILES}")
if(CMAKE_INSTALL_LOCAL_ONLY)
  file(WRITE "C:/Users/vlax6/AndroidStudioProjects/rpo/libs/mbedtls/build/x86/programs/install_local_manifest.txt"
     "${CMAKE_INSTALL_MANIFEST_CONTENT}")
endif()
