import urllib.request
import urllib.parse
import sys

# 配置
base_url = "http://localhost:8080/fortune-today"
params = {
    "fullName": "zhangsan",
    "birthDate": "1990-05-15"
}

# 构建 URL
full_url = f"{base_url}?{urllib.parse.urlencode(params)}"

print(f"正在请求: {full_url}")
print("-" * 50)

try:
    # 发起请求
    with urllib.request.urlopen(full_url) as response:
        # SSE 是基于文本行的协议，所以我们按行遍历
        for line in response:
            # 解码二进制数据
            decoded_line = line.decode('utf-8')
            
            # SSE 协议标准：数据行以 "data:" 开头
            if decoded_line.startswith("data:"):
                # 1. 去掉开头的 "data:" (5个字符)
                # 2. 去掉末尾的换行符 (.rstrip())
                fragment = decoded_line[5:].rstrip('\n')
                
                # 实时打印组装的内容
                # end="" 确保不换行，flush=True 确保字符立即显示
                print(fragment, end="", flush=True)

    print("\n" + "-" * 50)
    print("接收完成")

except urllib.error.URLError as e:
    print(f"\n连接错误: {e}")
except KeyboardInterrupt:
    print("\n用户停止")
