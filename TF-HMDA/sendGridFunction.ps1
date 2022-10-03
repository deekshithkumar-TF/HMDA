function Send-SendGridEmail {
  param(
    [Parameter(Mandatory = $true)]
    [String] $destEmailAddress,
    [Parameter(Mandatory = $true)]
    [String] $fromEmailAddress,
    [Parameter(Mandatory = $true)]
    [String] $subject,
    [Parameter(Mandatory = $false)]
    [string]$contentType = 'text/plain',
    [Parameter(Mandatory = $true)]
    [String] $contentBody,
    [parameter(Mandatory = $false)]
    [string]$FileName,
    [parameter(Mandatory = $false)]
    [string]$FileNameWithFilePath,
    [parameter(Mandatory = $false)]
    [string]$AttachementType
  )

  # Params
#$destEmailAddress = $destEmailAddress_value
#$fromEmailAddress = $fromEmailAddress_value

  <#
.Synopsis
Function to send email with SendGrid
.Description
A function to send a text or HTML based email
See https://sendgrid.com/docs/API_Reference/api_v3.html for API details
This script provided as-is with no warranty. Test it before you trust it.
www.ciraltos.com
.Parameter apiKey
The SendGrid API key associated with your account
.Parameter destEmailAddress
The destination email address
.Parameter fromEmailAddress
The from email address
.Parameter subject
Email subject
.Parameter type
The content type, values are “text/plain” or “text/html”.  "text/plain" set by default
.Parameter content
The content that you'd like to send
.Example
Send-SendGridEmail
#>
  ############ Update with your SendGrid API Key ####################
  $apiKey = "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"

  $headers = @{
    'Authorization' = 'Bearer ' + $apiKey
    'Content-Type'  = 'application/json'
  }
  #Convert File to Base64
  $FileContent = get-content $FileNameWithFilePath
  $ConvertToBytes = [System.Text.Encoding]::UTF8.GetBytes($FileContent)
  $EncodedFile = [System.Convert]::ToBase64String($ConvertToBytes)

  $body = @{
    personalizations = @(
      @{
        to = @(
          @{
            email = $destEmailAddress
          }
        )
      }
    )
    from             = @{
      email = $fromEmailAddress
    }
    subject          = $subject
    content          = @(
      @{
        type  = $contentType
        value = $contentBody
      }
    )
    attachments = @(
        @{
            content=$EncodedFile
            filename=$FileName
            type= $AttachementType
            disposition="attachment"
        }
    )
  }

  try {
    $bodyJson = $body | ConvertTo-Json -Depth 4
  }
  catch {
    $ErrorMessage = $_.Exception.message
    write-error ('Error converting body to json ' + $ErrorMessage)
    Break
  }

  try {
    Invoke-RestMethod -Uri https://api.sendgrid.com/v3/mail/send -Method Post -Headers $headers -Body $bodyJson 
  }
  catch {
    $ErrorMessage = $_.Exception.message
    write-error ('Error with Invoke-RestMethod ' + $ErrorMessage)
    Break
  }

}
$htmlBody = @"
<table>
        <tr>
               <td align="center">Hello,</td>
        </tr>
        <tr>
               <td align="center">Please find attached the sanity automation execution report.</td>
        </tr>
</table>
"@
$splat2 = @{
  destEmailAddress = 'sachinshastry95@gmail.com'
  fromEmailAddress = 'sachin.shastry@thoughtfocus.com'
  subject          = 'QA Sanity Automation Report'
  contentType      = 'text/html'
  contentBody      = $htmlBody
  FileName         = "emailable-report.html"
  FileNameWithFilePath = "D:\Selenium_Codebase\s\target\surefire-reports\emailable-report.html"
  AttachementType ="text/html"
}

Send-SendGridEmail @splat2

