set JHOME=C:\Program Files\IBM\SDP\runtimes\base_v61\java\jre
set TE_PROJECT_WEB_INF=C:\dev\TE_WkSpc\Subversion_latest\TimeAndExpense\WebContent\WEB-INF

echo "trying to enhance..."
"%JHOME%\bin\java" -cp "%TE_PROJECT_WEB_INF%\lib\openjpa-2.0.1.jar";"%TE_PROJECT_WEB_INF%\lib\commons-collections-3.2.1.jar";"%TE_PROJECT_WEB_INF%\lib\commons-lang-2.1.jar";"%TE_PROJECT_WEB_INF%\lib\commons-pool-1.3.jar";"%TE_PROJECT_WEB_INF%\lib\geronimo-jpa_2.0_spec-1.1.jar";"%TE_PROJECT_WEB_INF%\lib\geronimo-jta_1.1_spec-1.1.1.jar";"%TE_PROJECT_WEB_INF%\lib\serp-1.13.1.jar";"%TE_PROJECT_WEB_INF%\classes" org.apache.openjpa.enhance.PCEnhancer
pause
